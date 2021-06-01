package com.danshouseproject.project.moviecatalogue.data.remote.response.json

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JsonMoviesId(
    var moviesId: Int = 0
) : Parcelable


@Parcelize
data class JsonTvId(
    var tvShowsId: Int = 0
) : Parcelable