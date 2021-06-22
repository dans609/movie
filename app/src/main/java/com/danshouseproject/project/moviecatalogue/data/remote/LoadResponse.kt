package com.danshouseproject.project.moviecatalogue.data.remote

import androidx.lifecycle.LiveData
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.data.remote.status.ApiResponse

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