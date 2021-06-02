package com.danshouseproject.project.moviecatalogue.data.remote

import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.data.remote.response.json.JsonMoviesId
import com.danshouseproject.project.moviecatalogue.data.remote.response.json.JsonTvId
import com.danshouseproject.project.moviecatalogue.helper.RemoteJsonHelper

class RemoteDataSource private constructor(private val remoteJsonHelper: RemoteJsonHelper) {

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


    fun fetchMoviesId(): List<JsonMoviesId>? =
        remoteJsonHelper.fetchMoviesId()

    fun fetchTvId(): List<JsonTvId>? =
        remoteJsonHelper.fetchTvId()

    fun fetchMovies(id: Int, callback: LoadMoviesResponse) =
        remoteJsonHelper.fetchMovies(id, callback)

    fun fetchTvShows(id: Int, callback: LoadTvResponse) =
        remoteJsonHelper.fetchTvShows(id, callback)

    fun fetchMoviesMoreInfo(id: Int, callback: LoadMoviesMoreInfo) =
        remoteJsonHelper.fetchMoviesCertificate(id, callback)

    fun fetchTvMoreInfo(id: Int, callback: LoadTvMoreInfo) =
        remoteJsonHelper.fetchTvCertificate(id, callback)



    fun generateFilmDuration(duration: Int) =
        remoteJsonHelper.getStringForDuration(duration)

}