package com.danshouseproject.project.moviecatalogue.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel()
    }

    @Test
    fun getTvShows() {
        val tvShowsGenre = viewModel.getTvShows()
        assertNotNull(tvShowsGenre)
        assertEquals(MoviesViewModelTest.expextedFilmSize, tvShowsGenre.size)
    }
}