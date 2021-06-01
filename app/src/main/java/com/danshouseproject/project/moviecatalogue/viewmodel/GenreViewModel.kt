package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.FilmGenre

class GenreViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {

    fun getMoviesGenres(filmId: Int): LiveData<FilmGenre> =
        movieCatalogueRepository.getMoviesGenres(filmId)

    fun getTvShowsGenres(filmId: Int): LiveData<FilmGenre> =
        movieCatalogueRepository.getTvGenres(filmId)

}