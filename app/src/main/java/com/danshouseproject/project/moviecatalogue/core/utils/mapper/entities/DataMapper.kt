package com.danshouseproject.project.moviecatalogue.core.utils.mapper.entities

import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.GenreConverter
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.utils.mapper.MapperUtils
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

object DataMapper {
    private const val ZERO_VAL = 0
    private const val RES_MOVIES = 100
    private const val RES_MOVIES_GEN = 101
    private const val RES_MOVIES_INFO = 102
    private const val RES_TVS = 200
    private const val RES_TVS_GEN = 201
    private const val RES_TVS_INFO = 202
    const val EXTRA_MOVIES = "extra_movies"
    const val EXTRA_TV = "extra_tv"
    const val EXTRA_MOVIE_INFO = "extra_movies_info"
    const val EXTRA_TV_INFO = "extra_tv_info"

    private val mapping = mapOf<String, KClass<*>>(
        EXTRA_MOVIES to ResponseMovies::class,
        EXTRA_TV to ResponseTvShows::class,
        EXTRA_MOVIE_INFO to ResponseMoviesCertification::class,
        EXTRA_TV_INFO to ResponseTvCertification::class
    )

    fun <T> mapResponseToEntity(key: String, data: T): ListFilm? {
        val mClass = mapping[key]
        return when (mClass?.safeCast(data)) {
            is ResponseMovies -> castToEntity(data, RES_MOVIES)
            is ResponseTvShows -> castToEntity(data, RES_TVS)
            else -> null
        }
    }

    fun <T> mapResponseGenresToEntity(key: String, data: T): FilmGenre? {
        val mClass = mapping[key]
        return when (mClass?.safeCast(data)) {
            is ResponseMovies -> castToGenreEntity(data, RES_MOVIES_GEN)
            is ResponseTvShows -> castToGenreEntity(data, RES_TVS_GEN)
            else -> null
        }
    }

    fun <T> mapResponseInfoToEntity(key: String, data: T, filmId: Int): FilmInfo? {
        val mClass = mapping[key]
        return when (mClass?.safeCast(data)) {
            is ResponseMoviesCertification -> castToInfoEntity(data, RES_MOVIES_INFO, filmId)
            is ResponseTvCertification -> castToInfoEntity(data, RES_TVS_INFO, filmId)
            else -> null
        }
    }

    private fun <T> castToEntity(data: T, requestKey: Int): ListFilm {
        val instance = ListFilm()
        when (requestKey) {
            RES_MOVIES -> {
                data as ResponseMovies
                instance.filmId = data.moviesId
                instance.filmName = data.moviesTitle
                instance.filmDuration = MapperUtils.convertDuration(data.moviesDuration)
                instance.filmReleaseDate = data.moviesReleaseDate
                instance.filmOverview = data.moviesOverView
                instance.filmImage = MapperUtils.combinePath(data.moviesPosterPath)
                instance.filmScore = MapperUtils.convertScore(data.moviesScore)
                instance.isMovies = true
            }
            RES_TVS -> {
                data as ResponseTvShows
                instance.filmId = data.tvId
                instance.filmName = data.tvTitle
                instance.filmDuration = MapperUtils.convertDuration(data.tvDuration)
                instance.filmReleaseDate = data.tvReleaseDate
                instance.filmOverview = data.tvOverview
                instance.filmImage = MapperUtils.combinePath(data.tvPosterPath)
                instance.filmScore = MapperUtils.convertScore(data.tvScore)
            }
        }
        return instance
    }

    private fun <T> castToGenreEntity(data: T, requestKey: Int): FilmGenre? {
        val instance = FilmGenre()
        when (requestKey) {
            RES_MOVIES_GEN -> {
                data as ResponseMovies
                val responseGenre = data.moviesGenres ?: return null
                val listGenre = ArrayList<GenreConverter>()

                responseGenre.indices.map { idx ->
                    val genre = responseGenre[idx].genre
                    listGenre.add(GenreConverter(filmId = data.moviesId, genre = genre))
                }

                instance.filmId = data.moviesId
                instance.genre = listGenre
                instance.isMovies = true
            }
            RES_TVS_GEN -> {
                data as ResponseTvShows
                val responseGenres = data.tvGenres ?: return null
                val listGenre = ArrayList<GenreConverter>()

                responseGenres.indices.map { idx ->
                    val genre = responseGenres[idx].genre
                    listGenre.add(GenreConverter(filmId = data.tvId, genre = genre))
                }

                instance.filmId = data.tvId
                instance.genre = listGenre
            }
        }
        return instance
    }

    private fun <T> castToInfoEntity(data: T, requestKey: Int, filmId: Int): FilmInfo? {
        val instance = FilmInfo()
        when (requestKey) {
            RES_TVS_INFO -> {
                data as ResponseTvCertification
                val result = data.result ?: return null

                ZERO_VAL.let { zeroVal ->
                    instance.filmId = filmId
                    instance.isoCode = result[zeroVal].isoCode
                    instance.filmRating = result[zeroVal].certificate
                }
            }
            RES_MOVIES_INFO -> {
                data as ResponseMoviesCertification
                val result = data.result ?: return null
                var isDataFound = false

                instance.filmId = filmId
                instance.isMovies = true

                for (index in result.indices) {
                    when {
                        result[index].isoCode == "US" -> {
                            for (rating in result[index].moviesCertificate ?: return null) {
                                if (rating.certificate.isEmpty()) continue
                                else {
                                    instance.isoCode = result[index].isoCode
                                    instance.filmRating = rating.certificate
                                    isDataFound = true
                                    if (isDataFound) break
                                }
                            }
                            break
                        }
                        index == result.lastIndex && !isDataFound -> {
                            for (rating in result[index].moviesCertificate ?: return null) {
                                if (rating.certificate.isEmpty()) continue
                                else {
                                    instance.isoCode = result[index].isoCode
                                    instance.filmRating = rating.certificate
                                    break
                                }
                            }
                        }
                    }
                }
            }
        }
        return instance
    }

}