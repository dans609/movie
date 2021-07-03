package com.danshouseproject.project.moviecatalogue.core.components.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_film")
data class FavoriteFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "filmId")
    val filmId: Int = 0,

    @ColumnInfo(name = "posterPath")
    val posterPath: String = "",

    @ColumnInfo(name = "filmName")
    val filmName: String = "",

    @ColumnInfo(name = "overview")
    val overview: String = "",

    @ColumnInfo(name = "filmScore")
    var filmScore: Int = 0,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String = "",

    @ColumnInfo(name = "duration")
    var duration: String = "",

    @ColumnInfo(name = "isMovies")
    var isMovies: Boolean = false
)