package com.danshouseproject.project.moviecatalogue.api

import com.danshouseproject.project.moviecatalogue.api.tmdb.ApiKey
import com.danshouseproject.project.moviecatalogue.data.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers(ApiKey.ACCESS_TOKEN)
    @GET("3/movie/{id}")
    fun getMovies(@Path("id") id: String): Call<ResponseMovies>

    @Headers(ApiKey.ACCESS_TOKEN)
    @GET("3/tv/{id}")
    fun getTvShows(@Path("id") id: String): Call<ResponseTvShows>

    @Headers(ApiKey.ACCESS_TOKEN)
    @GET("3/movie/{id}/release_dates")
    fun getMoviesCertificate(@Path("id") id: String): Call<ResponseMoviesCertification>

    @Headers(ApiKey.ACCESS_TOKEN)
    @GET("3/tv/{id}/content_ratings")
    fun getTvCertificate(@Path("id") id: String): Call<ResponseTvCertification>

}