package com.danshouseproject.project.moviecatalogue.core.components.domain.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "filmgenre")
data class FilmGenre(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    @NonNull
    @ColumnInfo(name = "filmId")
    var filmId: Int = 0,

    @ColumnInfo(name = "genre")
    var genre: List<GenreConverter>? = null,

    @ColumnInfo(name = "isMovies")
    var isMovies: Boolean = false
)