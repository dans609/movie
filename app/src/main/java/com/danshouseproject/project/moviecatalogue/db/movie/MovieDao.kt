package com.danshouseproject.project.moviecatalogue.db.movie

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.model.ListFilm

@Dao
interface MovieDao {

    @Query("SELECT * FROM filmentities WHERE isMovies = :isMovies AND filmId = :filmId")
    fun getFilm(filmId: Int, isMovies: Boolean): LiveData<ListFilm>

    @Query("SELECT * FROM filmgenre WHERE filmId = :filmId AND isMovies = :isMovies")
    fun getFilmGenres(filmId: Int, isMovies: Boolean): LiveData<FilmGenre>

    @Query("SELECT * FROM filminfo WHERE filmId = :filmId AND isMovies = :isMovies LIMIT 1")
    fun getFilmInfo(filmId: Int, isMovies: Boolean): LiveData<FilmInfo>

    @Query("SELECT * FROM favorite_film WHERE isMovies = :isMovies")
    fun getAllFavoriteFilm(isMovies: Boolean): LiveData<List<FavoriteFilm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: ListFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilmGenres(listGenre: FilmGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilmInfo(filmAdditionalData: FilmInfo)

    @Insert
    fun addToFavorite(favoriteFilm: FavoriteFilm)

    @Query("DELETE FROM favorite_film WHERE favorite_film.filmId = :filmId")
    fun removeFromFavorite(filmId: Int): Int

    @Query("SELECT count(*) FROM favorite_film WHERE favorite_film.filmId = :filmId")
    fun checkIsFavorite(filmId: Int): Int
}