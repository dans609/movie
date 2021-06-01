package com.danshouseproject.project.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListFilm(
    var filmId: Int = 0,
    var filmName: String = "",
    var filmDuration: String = "",
    var filmReleaseDate: String = "",
    var filmRatingSymbol: String? = null,
    var filmCountryCode: String? = null,
    var filmOverview: String = "",
    var filmImage: String = "",
    var filmScore: Int = 0,

) : Parcelable