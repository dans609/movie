package com.danshouseproject.project.moviecatalogue.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource

class GenreViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {

    fun getMoviesGenres(filmId: Int): LiveData<Resource<FilmGenre>> =
        movieCatalogueRepository.getMoviesGenres(filmId)

    fun getTvShowsGenres(filmId: Int): LiveData<Resource<FilmGenre>> =
        movieCatalogueRepository.getTvGenres(filmId)

}