package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.utils.dummy.Movies
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource
import com.danshouseproject.project.moviecatalogue.view.home.branch.movies.MoviesViewModel
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
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<ListFilm>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieCatalogueRepository)
    }


    @Test
    fun getMovies() {
        val listMovies = ArrayList<ListFilm>()

        for (index in listMoviesId.indices) {
            val dummyMovies = Resource.success(Movies.generateMovies()[index])
            val responseMovies = MutableLiveData<Resource<ListFilm>>()

            responseMovies.value = dummyMovies
            listMovies.add(dummyMovies.data as ListFilm)

            `when`(movieCatalogueRepository.getAllMovies(listMoviesId[index])).thenReturn(
                responseMovies
            )
            val moviesEntity = viewModel.getMovies(listMoviesId[index]).value?.data as ListFilm
            verify(movieCatalogueRepository).getAllMovies(listMoviesId[index])

            moviesEntity.let {
                assertNotNull(it)
                assertNotNull(it.filmName)
                assertNotNull(it.filmOverview)
                assertTrue(it.isMovies)
                assertFalse(it.isOnFavorite)
                assertNotEquals(zeroVal, it.filmId)
                assertNotEquals(zeroVal, it.filmScore)

                val score = (RemoteMovies.responseMoviesScore[index] * teenVal).toInt()
                assertEquals(score, getResourceScore(it.filmScore))

                viewModel.getMovies(listMoviesId[index]).observeForever(observer)
                verify(observer).onChanged(dummyMovies)
            }
        }
        assertEquals(expextedFilmSize, listMovies.size)
    }


    companion object {
        const val zeroVal = 0
        const val teenVal = 10
        const val expextedFilmSize = 19
        val listMoviesId = Constant.getMoviesId
        private val moviesScore = Constant.getFilmScore

        private fun getResourceScore(scoreId: Int): Int? {
            var resourceScore: Int? = 0
            for (key in moviesScore.keys) {
                if (key == scoreId) {
                    resourceScore = moviesScore[scoreId]
                    break
                } else continue
            }
            return resourceScore
        }
    }
}