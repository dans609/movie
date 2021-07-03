package com.danshouseproject.project.moviecatalogue.core.components.data.local.lib.room.db.movie

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [FavoriteFilm::class])
    fun getOrderedFilm(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteFilm>

    @Query("SELECT * FROM filmentities WHERE isMovies = :isMovies AND filmId = :filmId")
    fun getFilm(filmId: Int, isMovies: Boolean): LiveData<ListFilm>

    @Query("SELECT * FROM filmgenre WHERE filmId = :filmId AND isMovies = :isMovies")
    fun getFilmGenres(filmId: Int, isMovies: Boolean): LiveData<FilmGenre>

    @Query("SELECT * FROM filminfo WHERE filmId = :filmId AND isMovies = :isMovies LIMIT 1")
    fun getFilmInfo(filmId: Int, isMovies: Boolean): LiveData<FilmInfo>

    @Query("SELECT * FROM favorite_film WHERE isMovies = :isMovies")
    fun getAllFavoriteFilm(isMovies: Boolean): DataSource.Factory<Int, FavoriteFilm>

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