package com.danshouseproject.project.moviecatalogue.core.components.data.remote

import com.danshouseproject.project.moviecatalogue.core.utils.helper.RemoteJsonHelper

class RemoteDataSource private constructor(private val remoteJsonHelper: RemoteJsonHelper) {

    fun fetchMovies(id: Int, callback: LoadMoviesResponse) =
        remoteJsonHelper.fetchMovies(id, callback)

    fun fetchTvShows(id: Int, callback: LoadTvResponse) =
        remoteJsonHelper.fetchTvShows(id, callback)

    fun fetchMoviesMoreInfo(id: Int, callback: LoadMoviesMoreInfo) =
        remoteJsonHelper.fetchMoviesCertificate(id, callback)

    fun fetchTvMoreInfo(id: Int, callback: LoadTvMoreInfo) =
        remoteJsonHelper.fetchTvCertificate(id, callback)


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: RemoteJsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply {
                    instance = this
                }
            }
    }
}