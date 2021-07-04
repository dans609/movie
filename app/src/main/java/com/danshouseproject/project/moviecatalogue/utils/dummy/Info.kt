package com.danshouseproject.project.moviecatalogue.utils.dummy

import com.danshouseproject.project.moviecatalogue.utils.dummy.Movies.moviesCountryCode
import com.danshouseproject.project.moviecatalogue.utils.dummy.Movies.moviesRating
import com.danshouseproject.project.moviecatalogue.utils.dummy.TvShows.tvShowsCountryCode
import com.danshouseproject.project.moviecatalogue.utils.dummy.TvShows.tvShowsRating
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteMovies.responseMoviesId
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteTvShows.responseTvShowsId
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo

object Info {
    private val moviesId get() = responseMoviesId
    private val moviesIso get() = moviesCountryCode
    private val moviesCertificate get() = moviesRating

    private val tvsId get() = responseTvShowsId
    private val tvIsoCode get() = tvShowsCountryCode
    private val tvCertificate get() = tvShowsRating

    fun generateMoviesInfo(): List<FilmInfo> {
        val list = ArrayList<FilmInfo>()

        for (index in moviesId.indices) {
            val moviesInfo = FilmInfo()
            moviesInfo.let {
                it.id = index
                it.filmId = moviesId[index]
                it.isoCode = moviesIso[index]
                it.filmRating = moviesCertificate[index]
                it.isMovies = true

                list.add(it)
            }
        }
        return list
    }

    fun generatetvShowsInfo(): List<FilmInfo> {
        val list = ArrayList<FilmInfo>()

        for (index in tvsId.indices) {
            val tvInfo = FilmInfo()
            tvInfo.let {
                it.id = index
                it.filmId = tvsId[index]
                it.isoCode = tvIsoCode[index]
                it.filmRating = tvCertificate[index]

                list.add(it)
            }
        }
        return list
    }
}