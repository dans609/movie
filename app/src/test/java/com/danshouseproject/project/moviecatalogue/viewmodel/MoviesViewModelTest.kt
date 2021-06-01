package com.danshouseproject.project.moviecatalogue.viewmodel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    companion object {
        const val expextedFilmSize = 19
    }

    @Before
    fun setUp() {
        viewModel = MoviesViewModel()
    }

    @Test
    fun getMovies() {
        val moviesEntities = viewModel.getMovies()
        assertNotNull(moviesEntities)
        assertEquals(expextedFilmSize, moviesEntities.size)
    }

}