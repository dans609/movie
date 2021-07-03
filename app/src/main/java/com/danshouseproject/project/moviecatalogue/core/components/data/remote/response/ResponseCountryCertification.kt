package com.danshouseproject.project.moviecatalogue.core.components.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTvCertification(
    @field:SerializedName("results")
    var result: List<TvCertificate>? = null
) : Parcelable

@Parcelize
data class TvCertificate(
    @field:SerializedName("iso_3166_1")
    var isoCode: String = "",

    @field:SerializedName("rating")
    var certificate: String = ""
) : Parcelable


@Parcelize
data class ResponseMoviesCertification(
    @field:SerializedName("results")
    var result: List<MovieCertificate>? = null
) : Parcelable

@Parcelize
data class MovieCertificate(
    @field:SerializedName("iso_3166_1")
    var isoCode: String = "",

    @field:SerializedName("release_dates")
    var moviesCertificate: List<Certificate>? = null
) : Parcelable

@Parcelize
data class Certificate(
    @field:SerializedName("certification")
    var certificate: String = ""
) : Parcelable