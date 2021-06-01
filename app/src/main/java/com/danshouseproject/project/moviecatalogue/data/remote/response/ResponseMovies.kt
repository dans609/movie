package com.danshouseproject.project.moviecatalogue.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseMovies(

    @field:SerializedName("poster_path")
    var moviesPosterPath: String = "",

    @field:SerializedName("id")
    var moviesId: Int = 0,

    @field:SerializedName("original_title")
    var moviesTitle: String = "",

    @field:SerializedName("runtime")
    var moviesDuration: Int = 0,

    @field:SerializedName("release_date")
    var moviesReleaseDate: String = "",

    @field:SerializedName("overview")
    var moviesOverView: String = "",

    @field:SerializedName("vote_average")
    var moviesScore: Double = 0.0,

    @field:SerializedName("genres")
    var moviesGenres: List<FetchFilmGenres>? = null

) : Parcelable