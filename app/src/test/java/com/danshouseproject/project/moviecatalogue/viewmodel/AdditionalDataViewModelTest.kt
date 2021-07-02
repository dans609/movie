package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.`object`.Info
import com.danshouseproject.project.moviecatalogue.`object`.Movies
import com.danshouseproject.project.moviecatalogue.`object`.TvShows
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModelTest.Companion.listMoviesId
import com.danshouseproject.project.moviecatalogue.viewmodel.TvShowsViewModelTest.Companion.listTvId
import com.danshouseproject.project.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdditionalDataViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AdditionalDataViewModel

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observerFilmInfo: Observer<Resource<FilmInfo>>

    @Mock
    private lateinit var observeFilmFavorite: Observer<PagedList<FavoriteFilm>>

    @Mock
    private lateinit var pagedList: PagedList<FavoriteFilm>

    @Mock
    private lateinit var observeInt: Observer<Int>

    @Before
    fun setUp() {
        viewModel = AdditionalDataViewModel(movieCatalogueRepository)
    }

    private fun castInt(data: String, getISO: Boolean): String? {
        return if (getISO) getISO(data.toInt())
        else getRating(data.toInt())
    }


    @Test
    fun getFavoriteFilm() {
        val dummyFilm = pagedList
        `when`(dummyFilm.size).thenReturn(fiveVal)

        val pagedListFilm = MutableLiveData<PagedList<FavoriteFilm>>()
        pagedListFilm.value = dummyFilm

        for (boolVal in listOf(true, false)) {
            `when`(movieCatalogueRepository.getAllFavoriteFilm(boolVal)).thenReturn(pagedListFilm)
            val favoriteEntity = viewModel.getFavoriteFilm(boolVal).value
            verify(movieCatalogueRepository).getAllFavoriteFilm(boolVal)

            assertNotNull(favoriteEntity)
            assertEquals(fiveVal, favoriteEntity?.size)

            viewModel.getFavoriteFilm(boolVal).observeForever(observeFilmFavorite)
            verify(observeFilmFavorite).onChanged(dummyFilm)
        }
    }


    @Test
    fun checkMoviesIsFavorite() {
        val dummyMovies = Movies.generateMovies()
        val dummyTvShows = TvShows.generateTvShows()

        listOf(listMoviesId, listTvId).indices.forEach { filmIdx ->
            val filmId = ArrayList<Int>()
            filmId.clear()

            if (filmIdx == zeroVal) filmId.addAll(listMoviesId)
            else filmId.addAll(listTvId)

            for (index in filmId.indices) {
                val isFavorite = MutableLiveData<Int>()
                val dummyFilm = if (filmIdx == zeroVal) dummyMovies else dummyTvShows

                isFavorite.value =
                    if (dummyFilm[index].isOnFavorite) oneVal
                    else zeroVal

                `when`(movieCatalogueRepository.checkIsFavorite(filmId[index])).thenReturn(isFavorite)
                val currentState = viewModel.checkIsFavorite(filmId[index]).value
                verify(movieCatalogueRepository).checkIsFavorite(filmId[index])

                assertNotNull(currentState)
                assertEquals(zeroVal, currentState)
                isFavorite.value = oneVal

                `when`(movieCatalogueRepository.checkIsFavorite(filmId[index])).thenReturn(isFavorite)
                verify(movieCatalogueRepository).checkIsFavorite(filmId[index])
                val newState = viewModel.checkIsFavorite(filmId[index]).value

                assertNotNull(newState)
                assertNotEquals(currentState, newState)
                assertEquals(oneVal, newState)
                viewModel.checkIsFavorite(filmId[index]).observeForever(observeInt)
            }
        }
    }

    @Test
    fun getMoviesAdditionalData() {
        val dummy = Info.generateMoviesInfo()

        for (index in listMoviesId.indices) {
            val dummyMoviesMoreInfo = Resource.success(dummy[index])
            val responseData = MutableLiveData<Resource<FilmInfo>>()
            responseData.value = dummyMoviesMoreInfo

            `when`(movieCatalogueRepository.getMoviesMoreInfo(listMoviesId[index])).thenReturn(
                responseData
            )
            val moviesInfoEntity =
                viewModel.getMoviesAdditionalData(listMoviesId[index]).value?.data as FilmInfo
            verify(movieCatalogueRepository).getMoviesMoreInfo(listMoviesId[index])

            moviesInfoEntity.let {
                val actualIsoCode = castInt(it.isoCode, true)
                val actualFilmRating = castInt(it.filmRating, false)

                assertNotNull(it)
                assertTrue(it.isMovies)
                assertEquals(index, it.id)

                doLoop(actualIsoCode, true)
                doLoop(actualFilmRating, false)
            }

            viewModel.getMoviesAdditionalData(listMoviesId[index]).observeForever(observerFilmInfo)
            verify(observerFilmInfo).onChanged(dummyMoviesMoreInfo)
        }
    }


    @Test
    fun getTvAdditionalData() {
        val dummy = Info.generatetvShowsInfo()

        for (index in listTvId.indices) {
            val dummyTvMoreInfo = Resource.success(dummy[index])
            val responseData = MutableLiveData<Resource<FilmInfo>>()
            responseData.value = dummyTvMoreInfo

            `when`(movieCatalogueRepository.getTvMoreInfo(listTvId[index])).thenReturn(responseData)
            val tvInfoEntity =
                viewModel.getTvAdditionalData(listTvId[index]).value?.data as FilmInfo
            verify(movieCatalogueRepository).getTvMoreInfo(listTvId[index])

            tvInfoEntity.let {
                val actualIso = castInt(it.isoCode, true)
                val actualRating = castInt(it.filmRating, false)

                assertNotNull(it)
                assertFalse(it.isMovies)
                assertEquals(index, it.id)

                doLoop(actualIso, true)
                doLoop(actualRating, false)
            }

            viewModel.getTvAdditionalData(listTvId[index]).observeForever(observerFilmInfo)
            verify(observerFilmInfo).onChanged(dummyTvMoreInfo)
        }
    }

    companion object {
        private const val zeroVal = 0
        private const val oneVal = 1
        private const val fiveVal = 5
        private val getAllRating: Map<Int, String> get() = Constant.getAllCertificate
        private val getAllISO: Map<Int, String> get() = Constant.getAllISO

        private fun getISO(keyCode: Int): String? = getAllISO[keyCode]
        private fun getRating(keyCode: Int): String? = getAllRating[keyCode]

        private fun doLoop(actualData: String?, doIsoLoop: Boolean) {
            if (actualData.isNullOrEmpty()) {
                println("Error: type missmatch")
                return
            }

            if (doIsoLoop) for (expectedIso in getAllISO.values) {
                if (expectedIso == actualData) {
                    assertEquals(expectedIso, actualData)
                    break
                } else continue
            } else for (expectedRating in getAllRating.values) {
                if (expectedRating == actualData) {
                    assertEquals(expectedRating, actualData)
                    break
                } else continue
            }
        }
    }
}