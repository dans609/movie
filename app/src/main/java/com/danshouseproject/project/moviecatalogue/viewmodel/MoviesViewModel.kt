package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.vo.Resource

class MoviesViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getMovies(id: Int): LiveData<Resource<ListFilm>> =
        movieCatalogueRepository.getAllMovies(id)

}