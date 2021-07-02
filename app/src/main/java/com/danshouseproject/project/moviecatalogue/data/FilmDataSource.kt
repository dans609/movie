package com.danshouseproject.project.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.vo.Resource

interface FilmDataSource {

    fun getOrderedFilm(sort: String): LiveData<PagedList<FavoriteFilm>>

    fun getAllMovies(filmId: Int): LiveData<Resource<ListFilm>>

    fun getAllTvShows(filmId: Int): LiveData<Resource<ListFilm>>

    fun getMoviesMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>>

    fun getTvMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>>

    fun getMoviesGenres(filmId: Int): LiveData<Resource<FilmGenre>>

    fun getTvGenres(filmId: Int): LiveData<Resource<FilmGenre>>

    fun addToFavorite(favoriteFilm: FavoriteFilm)

    fun removeFromFavorite(filmId: Int)

    fun checkIsFavorite(filmId: Int): LiveData<Int>

    fun getAllFavoriteFilm(isMovies: Boolean): LiveData<PagedList<FavoriteFilm>>
}