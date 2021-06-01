package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm

class TvShowsViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getTvShows(): LiveData<List<ListFilm>> =
        movieCatalogueRepository.getAllTvShows()

}