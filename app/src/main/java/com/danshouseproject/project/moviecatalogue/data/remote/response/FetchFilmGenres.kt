package com.danshouseproject.project.moviecatalogue.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FetchFilmGenres(

    @field:SerializedName("name")
    var genre: String = ""

) : Parcelable