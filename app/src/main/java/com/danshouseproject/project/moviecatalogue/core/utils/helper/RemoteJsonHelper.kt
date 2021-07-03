package com.danshouseproject.project.moviecatalogue.core.utils.helper

import android.util.Log
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.LoadMoviesMoreInfo
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.LoadMoviesResponse
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.LoadTvMoreInfo
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.LoadTvResponse
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit.RetrofitClient
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.status.ApiResponse
import com.danshouseproject.project.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJsonHelper {

    private var isSuccess = false
    private var message: String? = null
    private fun setConditionForGetCallback(statusResponse: Boolean, msg: String? = null) {
        isSuccess = statusResponse
        message = msg
    }

    private fun <T, R> T.wrapResponse(
        callback: R,
        objectCallback: R.(ApiResponse<T>) -> Unit
    ) {
        if (isSuccess) callback.objectCallback(ApiResponse.success(this))
        else callback.objectCallback(ApiResponse.empty(this, message.toString()))
    }

    private fun failResMessage(respThrow: Throwable) =
        Log.d(TAG, respThrow.message.toString())

    private fun checkIdleCondition() {
        if (!EspressoIdlingResource.getEspressoIdlingResourceForActivity().isIdleNow)
            EspressoIdlingResource.decrement()
    }


    fun fetchMoviesCertificate(id: Int, callback: LoadMoviesMoreInfo) {
        EspressoIdlingResource.increment()
        RetrofitClient.apiService()
            .getMoviesCertificate(id.toString())
            .enqueue(object : Callback<ResponseMoviesCertification> {
                override fun onResponse(
                    call: Call<ResponseMoviesCertification>,
                    response: Response<ResponseMoviesCertification>
                ) {
                    val movieInfoResponse = response.body() as ResponseMoviesCertification
                    if (response.isSuccessful) setConditionForGetCallback(SUCCESS_CONDITION)
                    else setConditionForGetCallback(EMPTY_CONDITION, response.message())

                    movieInfoResponse.wrapResponse(callback, { additionalData ->
                        onMoviesAdditionInformationReceived(additionalData)
                    })
                    checkIdleCondition()
                }

                override fun onFailure(call: Call<ResponseMoviesCertification>, t: Throwable) {
                    failResMessage(t)
                    ApiResponse.error<ResponseMoviesCertification>(t.message.toString())
                }
            })
    }


    fun fetchTvCertificate(id: Int, callback: LoadTvMoreInfo) {
        EspressoIdlingResource.increment()
        RetrofitClient.apiService()
            .getTvCertificate(id.toString())
            .enqueue(object : Callback<ResponseTvCertification> {
                override fun onResponse(
                    call: Call<ResponseTvCertification>,
                    response: Response<ResponseTvCertification>
                ) {
                    val tvInfoResponse = response.body() as ResponseTvCertification
                    if (response.isSuccessful) setConditionForGetCallback(SUCCESS_CONDITION)
                    else setConditionForGetCallback(EMPTY_CONDITION, response.message())

                    tvInfoResponse.wrapResponse(callback, { additionalData ->
                        onTvShowsAdditionInformatonReceived(additionalData)
                    })
                    checkIdleCondition()
                }

                override fun onFailure(call: Call<ResponseTvCertification>, t: Throwable) {
                    failResMessage(t)
                    ApiResponse.error<ResponseTvCertification>(t.message.toString())
                }
            })
    }


    fun fetchMovies(id: Int, callback: LoadMoviesResponse) {
        EspressoIdlingResource.increment()
        RetrofitClient.apiService()
            .getMovies(id.toString())
            .enqueue(object : Callback<ResponseMovies?> {
                override fun onResponse(
                    call: Call<ResponseMovies?>,
                    response: Response<ResponseMovies?>
                ) {
                    val movieResponse = response.body() as ResponseMovies
                    if (response.isSuccessful) setConditionForGetCallback(SUCCESS_CONDITION)
                    else setConditionForGetCallback(EMPTY_CONDITION, response.message())

                    movieResponse.wrapResponse(callback, { data -> onMoviesLoaded(data) })
                    checkIdleCondition()
                }

                override fun onFailure(call: Call<ResponseMovies?>, t: Throwable) {
                    failResMessage(t)
                    ApiResponse.error<ResponseMovies>(t.message.toString())
                }
            })
    }


    fun fetchTvShows(id: Int, callback: LoadTvResponse) {
        EspressoIdlingResource.increment()
        RetrofitClient.apiService()
            .getTvShows(id.toString())
            .enqueue(object : Callback<ResponseTvShows?> {
                override fun onResponse(
                    call: Call<ResponseTvShows?>,
                    response: Response<ResponseTvShows?>
                ) {
                    val tvResponse = response.body() as ResponseTvShows
                    if (response.isSuccessful) setConditionForGetCallback(SUCCESS_CONDITION)
                    else setConditionForGetCallback(EMPTY_CONDITION, response.message())

                    tvResponse.wrapResponse(callback, { data -> onTvShowsLoaded(data) })
                    checkIdleCondition()
                }

                override fun onFailure(call: Call<ResponseTvShows?>, t: Throwable) {
                    failResMessage(t)
                    ApiResponse.error<ResponseTvShows>(t.message.toString())
                }
            })
    }


    companion object {
        private val TAG = RemoteJsonHelper::class.java.simpleName
        private const val EMPTY_CONDITION = false
        private const val SUCCESS_CONDITION = true
    }
}