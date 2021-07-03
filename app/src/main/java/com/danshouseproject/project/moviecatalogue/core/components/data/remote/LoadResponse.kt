package com.danshouseproject.project.moviecatalogue.core.components.data.remote

import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.status.ApiResponse

interface LoadMoviesResponse {
    fun onMoviesLoaded(data: ApiResponse<ResponseMovies>)
}

interface LoadTvResponse {
    fun onTvShowsLoaded(data: ApiResponse<ResponseTvShows>)
}

interface LoadMoviesMoreInfo {
    fun onMoviesAdditionInformationReceived(additionalData: ApiResponse<ResponseMoviesCertification>)
}

interface LoadTvMoreInfo {
    fun onTvShowsAdditionInformatonReceived(additionalData: ApiResponse<ResponseTvCertification>)
}