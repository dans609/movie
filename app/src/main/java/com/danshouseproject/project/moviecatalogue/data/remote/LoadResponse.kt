package com.danshouseproject.project.moviecatalogue.data.remote

import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvShows

interface LoadMoviesResponse {
    fun onMoviesLoaded(data: ResponseMovies)
}

interface LoadTvResponse {
    fun onTvShowsLoaded(data: ResponseTvShows)
}

interface LoadMoviesMoreInfo {
    fun onMoviesAdditionInformationReceived(additionalData: ResponseMoviesCertification)
}

interface LoadTvMoreInfo {
    fun onTvShowsAdditionInformatonReceived(additionalData: ResponseTvCertification)
}