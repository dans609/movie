package com.danshouseproject.project.moviecatalogue.view.home.branch.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource

class TvShowsViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getTvShows(id: Int): LiveData<Resource<ListFilm>> =
        movieCatalogueRepository.getAllTvShows(id)

}