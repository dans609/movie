package com.danshouseproject.project.moviecatalogue.helper

import android.util.Log
import com.danshouseproject.project.moviecatalogue.data.remote.LoadMoviesMoreInfo
import com.danshouseproject.project.moviecatalogue.data.remote.LoadMoviesResponse
import com.danshouseproject.project.moviecatalogue.data.remote.LoadTvMoreInfo
import com.danshouseproject.project.moviecatalogue.data.remote.LoadTvResponse
import com.danshouseproject.project.moviecatalogue.data.remote.build.RetrofitClient
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvShows
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJsonHelper {

    companion object {
        private val TAG = RemoteJsonHelper::class.java.simpleName
    }

    private fun failResMessage(respThrow: Throwable) =
        Log.d(TAG, respThrow.message.toString())


    fun fetchMoviesCertificate(id: Int, callback: LoadMoviesMoreInfo) {
        RetrofitClient.apiService()
            .getMoviesCertificate(id.toString())
            .enqueue(object : Callback<ResponseMoviesCertification> {
                override fun onResponse(
                    call: Call<ResponseMoviesCertification>,
                    response: Response<ResponseMoviesCertification>
                ) {
                    if (response.isSuccessful)
                        callback.onMoviesAdditionInformationReceived(response.body() as ResponseMoviesCertification)
                }

                override fun onFailure(call: Call<ResponseMoviesCertification>, t: Throwable) {
                    failResMessage(t)
                }
            })
    }


    fun fetchTvCertificate(id: Int, callback: LoadTvMoreInfo) {
        RetrofitClient.apiService()
            .getTvCertificate(id.toString())
            .enqueue(object : Callback<ResponseTvCertification> {
                override fun onResponse(
                    call: Call<ResponseTvCertification>,
                    response: Response<ResponseTvCertification>
                ) {
                    if (response.isSuccessful)
                        callback.onTvShowsAdditionInformatonReceived(response.body() as ResponseTvCertification)
                }

                override fun onFailure(call: Call<ResponseTvCertification>, t: Throwable) {
                    failResMessage(t)
                }
            })
    }


    fun fetchMovies(id: Int, callback: LoadMoviesResponse) {
        RetrofitClient.apiService()
            .getMovies(id.toString())
            .enqueue(object : Callback<ResponseMovies?> {
                override fun onResponse(
                    call: Call<ResponseMovies?>,
                    response: Response<ResponseMovies?>
                ) {
                    if (response.isSuccessful)
                        callback.onMoviesLoaded(response.body() as ResponseMovies)
                }

                override fun onFailure(call: Call<ResponseMovies?>, t: Throwable) {
                    failResMessage(t)
                }
            })
    }


    fun fetchTvShows(id: Int, callback: LoadTvResponse) {
        RetrofitClient.apiService()
            .getTvShows(id.toString())
            .enqueue(object : Callback<ResponseTvShows?> {
                override fun onResponse(
                    call: Call<ResponseTvShows?>,
                    response: Response<ResponseTvShows?>
                ) {
                    if (response.isSuccessful)
                        callback.onTvShowsLoaded(response.body() as ResponseTvShows)
                }

                override fun onFailure(call: Call<ResponseTvShows?>, t: Throwable) {
                    failResMessage(t)
                }
            })
    }

}