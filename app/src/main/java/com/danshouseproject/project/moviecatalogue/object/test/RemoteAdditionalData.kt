package com.danshouseproject.project.moviecatalogue.`object`.test

import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteMovies.responseMoviesId
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteTvShows.responseTvShowsId
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.CN_ISO_CODE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.DE_ISO_CODE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.EQUALS_OR_OLDER_T12
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.EQUALS_OR_OLDER_T16
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.EQUALS_OR_OLDER_T18
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.EQUALS_OT_OLDER_T15
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.GB_ISO_CODE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.MORE_THAN_TWELVE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.PG13_MOVIE_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.PG_MOVIE_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.R_MOVIE_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.TV14_TV_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.TVMA_TV_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.TVPG_TV_CERTIFICATE
import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant.US_ISO_CODE
import com.danshouseproject.project.moviecatalogue.data.remote.response.*

object RemoteAdditionalData {

    private val moviesId: List<Int> = responseMoviesId
    private val tvId: List<Int> = responseTvShowsId

    private val moviesIso: List<String> = listOf(
        US_ISO_CODE, US_ISO_CODE, US_ISO_CODE, US_ISO_CODE,
        US_ISO_CODE, US_ISO_CODE, US_ISO_CODE, US_ISO_CODE,
        US_ISO_CODE, US_ISO_CODE, US_ISO_CODE, US_ISO_CODE,
        US_ISO_CODE, US_ISO_CODE, US_ISO_CODE, US_ISO_CODE,
        US_ISO_CODE, US_ISO_CODE, CN_ISO_CODE
    )

    private val moviesCertificate: List<String> = listOf(
        R_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE,
        R_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE,
        PG_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE, R_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE,
        PG13_MOVIE_CERTIFICATE, R_MOVIE_CERTIFICATE, PG_MOVIE_CERTIFICATE, PG13_MOVIE_CERTIFICATE,
        R_MOVIE_CERTIFICATE, PG_MOVIE_CERTIFICATE, MORE_THAN_TWELVE
    )

    fun generateMoviesAdditionalDataResponse(filmId: Int): ResponseMoviesCertification {
        val instanciateResponse = ResponseMoviesCertification()
        for (index in moviesId.indices)
            if (filmId == moviesId[index]) {
                instanciateResponse.result = listOf(
                    MovieCertificate(
                        moviesIso[index],
                        listOf(Certificate(moviesCertificate[index]))
                    )
                )
                break
            } else continue

        return instanciateResponse
    }


    private val tvIso: List<String> = listOf(
        DE_ISO_CODE, US_ISO_CODE, US_ISO_CODE, GB_ISO_CODE,
        GB_ISO_CODE, DE_ISO_CODE, DE_ISO_CODE, DE_ISO_CODE,
        DE_ISO_CODE, US_ISO_CODE, US_ISO_CODE, GB_ISO_CODE,
        DE_ISO_CODE, US_ISO_CODE, GB_ISO_CODE, DE_ISO_CODE,
        DE_ISO_CODE, DE_ISO_CODE, US_ISO_CODE
    )

    private val tvCertificate: List<String> = listOf(
        EQUALS_OR_OLDER_T16, TVMA_TV_CERTIFICATE, TVPG_TV_CERTIFICATE, EQUALS_OR_OLDER_T12,
        EQUALS_OT_OLDER_T15, EQUALS_OR_OLDER_T12, EQUALS_OR_OLDER_T16, EQUALS_OR_OLDER_T16,
        EQUALS_OR_OLDER_T16, TVMA_TV_CERTIFICATE, TVMA_TV_CERTIFICATE, EQUALS_OR_OLDER_T12,
        EQUALS_OR_OLDER_T16, TV14_TV_CERTIFICATE, EQUALS_OR_OLDER_T18, EQUALS_OR_OLDER_T12,
        EQUALS_OR_OLDER_T16, EQUALS_OR_OLDER_T12, TVMA_TV_CERTIFICATE

    )

    fun generateTvsAdditionalDataResponse(filmId: Int): ResponseTvCertification {
        val instanciateResponse = ResponseTvCertification()
        for (index in tvId.indices)
            if (filmId == tvId[index]) {
                instanciateResponse.result =
                    listOf(TvCertificate(tvIso[index], tvCertificate[index]))
                break
            } else continue

        return instanciateResponse
    }
}