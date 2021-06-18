package com.danshouseproject.project.moviecatalogue.data.remote

import com.danshouseproject.project.moviecatalogue.helper.EspressoIdlingResource
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

    private fun checkIdleCondition() {
        if (!EspressoIdlingResource.getEspressoIdlingResourceForActivity().isIdleNow)
            EspressoIdlingResource.decrement()
    }

    fun fetchMovies(id: Int, callback: LoadMoviesResponse) {
        EspressoIdlingResource.increment()
        remoteJsonHelper.fetchMovies(id, callback)
        checkIdleCondition()
    }

    fun fetchTvShows(id: Int, callback: LoadTvResponse) {
        EspressoIdlingResource.increment()
        remoteJsonHelper.fetchTvShows(id, callback)
        checkIdleCondition()
    }

    fun fetchMoviesMoreInfo(id: Int, callback: LoadMoviesMoreInfo) {
        EspressoIdlingResource.increment()
        remoteJsonHelper.fetchMoviesCertificate(id, callback)
        checkIdleCondition()
    }

    fun fetchTvMoreInfo(id: Int, callback: LoadTvMoreInfo) {
        EspressoIdlingResource.increment()
        remoteJsonHelper.fetchTvCertificate(id, callback)
        checkIdleCondition()
    }

}