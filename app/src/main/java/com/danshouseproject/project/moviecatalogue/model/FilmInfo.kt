package com.danshouseproject.project.moviecatalogue.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filminfo")
data class FilmInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "filmId")
    var filmId: Int = 0,

    @ColumnInfo(name = "isoCode")
    var isoCode: String = "",

    @ColumnInfo(name = "filmRating")
    var filmRating: String = "",

    @ColumnInfo(name = "isMovies")
    var isMovies: Boolean = false
)