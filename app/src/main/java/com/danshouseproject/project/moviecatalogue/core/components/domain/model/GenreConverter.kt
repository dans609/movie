package com.danshouseproject.project.moviecatalogue.core.components.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreConverter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "filmId")
    val filmId: Int,

    @ColumnInfo(name = "genre")
    val genre: String
)
