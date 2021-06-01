package com.danshouseproject.project.moviecatalogue.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GenreViewModelTest {

    private lateinit var viewModel: GenreViewModel

    companion object {
        private const val VALUE_ZERO_FROM_R = 2131361863
        private const val VALUE_EIGHTTEEN_FROM_R = 2131361812
        private const val VALUE_DRAMA_FROM_R = 2131755089
        private const val VALUE_ROMANCE_FROM_R = 2131755097
        private const val VALUE_MUSIC_FROM_R = 2131755095
        private const val VALUE_ACTION_ADVENTURE_FROM_R = 2131755084
        private const val VALUE_SCIFI_FANTASY_FROM_R = 2131755099
    }

    @Before
    fun setUp() {
        viewModel = GenreViewModel()
    }

    @Test
    fun getMoviesGenres() {
        val movieGenres = viewModel.getMoviesGenres()
        assertNotNull(movieGenres)
        assertEquals(MoviesViewModelTest.expextedFilmSize, movieGenres.size)
    }

    @Test
    fun getTvShowsGenres() {
        val tvGenres = viewModel.getTvShowsGenres()
        assertNotNull(tvGenres)
        assertEquals(MoviesViewModelTest.expextedFilmSize, tvGenres.size)
    }

    @Test
    fun getMoviesGenreFilmId() {
        val moviesId = viewModel.getMoviesGenres()
        assertNotNull(moviesId[0].filmId)
        assertEquals(VALUE_ZERO_FROM_R, moviesId[0].filmId)
    }

    @Test
    fun getTvShowsGenreFilmId() {
        val tvShowsId = viewModel.getTvShowsGenres()
        assertNotNull(tvShowsId[tvShowsId.size - 1].filmId)
        assertEquals(VALUE_EIGHTTEEN_FROM_R, tvShowsId[tvShowsId.size - 1].filmId)
    }

    @Test
    fun getMoviesGenreValue() {
        val moviesGenreId = viewModel.getMoviesGenres()
        val arrayGenre = arrayListOf(VALUE_DRAMA_FROM_R, VALUE_ROMANCE_FROM_R, VALUE_MUSIC_FROM_R)

        assertEquals(0, moviesGenreId[0].genre?.indexOf(arrayGenre[0]))

        for (index in arrayGenre.indices) {
            assertEquals(arrayGenre[index], moviesGenreId[0].genre?.get(index))
        }
    }

    @Test
    fun getTvShowsGenreValue() {
        val tvShowsGenreId = viewModel.getTvShowsGenres()
        val arrayGenre = arrayListOf(
            VALUE_ACTION_ADVENTURE_FROM_R,
            VALUE_SCIFI_FANTASY_FROM_R,
            VALUE_DRAMA_FROM_R
        )

        assertEquals(2, tvShowsGenreId[tvShowsGenreId.size - 1].genre?.indexOf(arrayGenre[2]))

        for (index in arrayGenre.indices) {
            assertEquals(
                arrayGenre[index],
                tvShowsGenreId[tvShowsGenreId.size - 1].genre?.get(index)
            )
        }
    }

}