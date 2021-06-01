package com.danshouseproject.project.moviecatalogue.api.tmdb

import com.danshouseproject.project.moviecatalogue.BuildConfig

object ApiKey {
    const val APIKEY = BuildConfig.TMDB_API_KEY
    const val ACCESS_TOKEN = BuildConfig.TMDB_ACCESS_TOKEN

    const val BASE_URL = "https://api.themoviedb.org/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
}