package com.danshouseproject.project.moviecatalogue.view.home.branch.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource

class MoviesViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getMovies(id: Int): LiveData<Resource<ListFilm>> =
        movieCatalogueRepository.getAllMovies(id)

}