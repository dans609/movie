package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.vo.Resource

class TvShowsViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getTvShows(id: Int): LiveData<Resource<ListFilm>> =
        movieCatalogueRepository.getAllTvShows(id)

}