package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.`object`.Movies
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
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
    private lateinit var observer: Observer<ListFilm>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieCatalogueRepository)
    }

    companion object {
        const val expextedFilmSize = 19
        private const val ASIB_ID = 332562
        private const val ALITA_ID = 399579
        private const val AQUAMAN_ID = 297802
        private const val BOHEMIAN_R_ID = 424694
        private const val COLD_PURST_ID = 438650
        private const val CREED_II_ID = 480530
        private const val FB_GRINDEL_ID = 338952
        private const val GLASS_ID = 450465
        private const val HOW_TTYD_ID = 166428
        private const val AVENGER_ID = 299536
        private const val MARY_QUEEN_ID = 457136
        private const val IPMAN_ID = 450001
        private const val MORTAL_ENG_ID = 428078
        private const val OVERLORD_ID = 438799
        private const val RALPH_ID = 404368
        private const val ROBIN_HOOD_ID = 375588
        private const val SERENITY_ID = 452832
        private const val SPIDERMAN_ID = 324857
        private const val T34_ID = 505954
    }

    @Test
    fun getMovies() {
        val dummyMovies = Movies.generateMovies()
        val listMovies = ArrayList<ListFilm>()

        val listMoviesId = listOf(
            ASIB_ID, ALITA_ID, AQUAMAN_ID, BOHEMIAN_R_ID, COLD_PURST_ID,
            CREED_II_ID, FB_GRINDEL_ID, GLASS_ID, HOW_TTYD_ID, AVENGER_ID,
            MARY_QUEEN_ID, IPMAN_ID, MORTAL_ENG_ID, OVERLORD_ID, RALPH_ID,
            ROBIN_HOOD_ID, SERENITY_ID, SPIDERMAN_ID, T34_ID
        )

        for (index in listMoviesId.indices) {
            val movies = MutableLiveData<ListFilm>()
            movies.value = dummyMovies[index]
            listMovies.add(dummyMovies[index])

            `when`(movieCatalogueRepository.getAllMovies(listMoviesId[index])).thenReturn(movies)
            val getMovies = viewModel.getMovies(listMoviesId[index]).value
            verify(movieCatalogueRepository).getAllMovies(listMoviesId[index])

            assertNotNull(getMovies)
            assertEquals(getMovies?.filmScore, dummyMovies[index].filmScore)

            viewModel.getMovies(listMoviesId[index]).observeForever(observer)
            verify(observer).onChanged(dummyMovies[index])
        }

        assertEquals(expextedFilmSize, listMovies.size)
    }

}