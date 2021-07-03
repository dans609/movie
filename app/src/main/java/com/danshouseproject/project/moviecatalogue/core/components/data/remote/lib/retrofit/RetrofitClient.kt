package com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit

import com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit.api.ApiService
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit.api.tmdb.ApiKey.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private fun setInterceptor(): OkHttpClient =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            .let { httpLogInterceptor ->
                return OkHttpClient.Builder()
                    .addInterceptor(httpLogInterceptor)
                    .build()
            }

    private fun setUpClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setInterceptor())
            .build()

    fun apiService(): ApiService =
        setUpClient().create(ApiService::class.java)

}