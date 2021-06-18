package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository

class AdditionalDataViewModel(private val mMovieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getMoviesAdditionalData(filmId: Int): LiveData<Pair<String, String>> =
        mMovieCatalogueRepository.getMoviesMoreInfo(filmId)

    fun getTvAdditionalData(filmId: Int): LiveData<Pair<String, String>> =
        mMovieCatalogueRepository.getTvMoreInfo(filmId)

}