package com.danshouseproject.project.moviecatalogue.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ResponseTvShows(

    @field:SerializedName("poster_path")
    var tvPosterPath: String = "",

    @field:SerializedName("id")
    var tvId: Int = 0,

    @field:SerializedName("name")
    var tvTitle: String = "",

    @field:SerializedName("episode_run_time")
    var tvDuration: List<Int>? = null,

    @field:SerializedName("first_air_date")
    var tvReleaseDate: String = "",

    @field:SerializedName("overview")
    var tvOverview: String = "",

    @field:SerializedName("vote_average")
    var tvScore: Double = 0.0,

    @field:SerializedName("genres")
    var tvGenres: List<FetchFilmGenres>? = null

) : Parcelable