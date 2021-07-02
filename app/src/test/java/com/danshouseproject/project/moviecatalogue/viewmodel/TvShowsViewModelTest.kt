package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.`object`.TvShows
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModelTest.Companion.expextedFilmSize
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModelTest.Companion.teenVal
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<ListFilm>>

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(movieCatalogueRepository)
    }

    @Test
    fun getTvShows() {
        val listTvs = ArrayList<ListFilm>()

        for (index in listTvId.indices) {
            val dummyTv = Resource.success(TvShows.generateTvShows()[index])
            val responseTv = MutableLiveData<Resource<ListFilm>>()

            responseTv.value = dummyTv
            listTvs.add(dummyTv.data as ListFilm)

            `when`(movieCatalogueRepository.getAllTvShows(listTvId[index])).thenReturn(responseTv)
            val tvShowsEntity = viewModel.getTvShows(listTvId[index]).value?.data as ListFilm
            verify(movieCatalogueRepository).getAllTvShows(listTvId[index])

            tvShowsEntity.let {
                assertNotNull(it)
                assertNotNull(it.filmName)
                assertNotNull(it.filmOverview)
                assertFalse(it.isMovies)
                assertFalse(it.isOnFavorite)
                assertNotEquals(zeroVal, it.filmId)
                assertNotEquals(zeroVal, it.filmScore)

                val score = (RemoteTvShows.responseTvShowsScore[index] * teenVal).toInt()
                assertEquals(score, getResourceScore(it.filmScore))

                viewModel.getTvShows(listTvId[index]).observeForever(observer)
                verify(observer).onChanged(dummyTv)
            }
        }
        assertEquals(expextedFilmSize, listTvs.size)
    }

    companion object {
        private const val zeroVal = 0
        private val tvsScore = Constant.getFilmScore
        val listTvId = Constant.getTvId

        private fun getResourceScore(scoreId: Int): Int? {
            var resourceScore: Int? = zeroVal
            for (key in tvsScore.keys) {
                if (key == scoreId) {
                    resourceScore = tvsScore[scoreId]
                    break
                } else continue
            }
            return resourceScore
        }
    }
}