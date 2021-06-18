package com.danshouseproject.project.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.ListFilm

interface FilmDataSource {

    fun getAllMovies(filmId: Int): LiveData<ListFilm>

    fun getAllTvShows(filmId: Int): LiveData<ListFilm>

    fun getMoviesMoreInfo(filmId: Int): LiveData<Pair<String, String>>

    fun getTvMoreInfo(filmId: Int): LiveData<Pair<String, String>>

    fun getMoviesGenres(filmId: Int): LiveData<FilmGenre>

    fun getTvGenres(filmId: Int): LiveData<FilmGenre>

}