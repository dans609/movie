package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.vo.Resource

class GenreViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {

    fun getMoviesGenres(filmId: Int): LiveData<Resource<FilmGenre>> =
        movieCatalogueRepository.getMoviesGenres(filmId)

    fun getTvShowsGenres(filmId: Int): LiveData<Resource<FilmGenre>> =
        movieCatalogueRepository.getTvGenres(filmId)

}