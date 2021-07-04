package com.danshouseproject.project.moviecatalogue.core.components.domain.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "filmentities")
@Parcelize
data class ListFilm(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "filmId")
    var filmId: Int = 0,

    @ColumnInfo(name = "filmName")
    var filmName: String = "",

    @ColumnInfo(name = "filmDuration")
    var filmDuration: String = "",

    @ColumnInfo(name = "filmReleaseDate")
    var filmReleaseDate: String = "",

    @ColumnInfo(name = "filmOverview")
    var filmOverview: String = "",

    @ColumnInfo(name = "filmImage")
    var filmImage: String = "",

    @ColumnInfo(name = "filmScore")
    var filmScore: Int = 0,

    @ColumnInfo(name = "isMovies")
    var isMovies: Boolean = false,

    @ColumnInfo(name = "isOnFavorite")
    var isOnFavorite: Boolean = false

) : Parcelable