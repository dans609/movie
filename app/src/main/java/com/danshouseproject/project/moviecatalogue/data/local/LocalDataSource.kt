package com.danshouseproject.project.moviecatalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.danshouseproject.project.moviecatalogue.db.movie.MovieDao
import com.danshouseproject.project.moviecatalogue.helper.utils.SortUtils
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.model.ListFilm

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {

    fun getOrderedFilm(sort: String): DataSource.Factory<Int, FavoriteFilm> =
        SortUtils.getSortedQuery(sort).let { query ->
            mMovieDao.getOrderedFilm(query)
        }

    fun getFilm(filmId: Int, isMovies: Boolean): LiveData<ListFilm> =
        mMovieDao.getFilm(filmId, isMovies)

    fun getFilmGenre(filmId: Int, isMovies: Boolean): LiveData<FilmGenre> =
        mMovieDao.getFilmGenres(filmId, isMovies)

    fun getFilmInfo(filmId: Int, isMovies: Boolean): LiveData<FilmInfo> =
        mMovieDao.getFilmInfo(filmId, isMovies)

    fun insertFilm(filmEntity: ListFilm) =
        mMovieDao.insertFilm(filmEntity)

    fun insertFilmGenres(filmGenre: FilmGenre) =
        mMovieDao.insertFilmGenres(filmGenre)

    fun insertFilmInfo(filmInfo: FilmInfo) =
        mMovieDao.insertFilmInfo(filmInfo)

    fun addToFavorite(filmFavorite: FavoriteFilm) =
        mMovieDao.addToFavorite(filmFavorite)

    fun removeFromFavorite(filmId: Int): Int =
        mMovieDao.removeFromFavorite(filmId)

    fun checkIsFavorite(filmId: Int): Int =
        mMovieDao.checkIsFavorite(filmId)

    fun getAllFavoriteFilm(isMovies: Boolean): DataSource.Factory<Int, FavoriteFilm> =
        mMovieDao.getAllFavoriteFilm(isMovies)

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getLocalInstance(movieDao: MovieDao) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(movieDao)
            }
    }
}