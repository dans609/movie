package com.danshouseproject.project.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danshouseproject.project.moviecatalogue.api.tmdb.ApiKey
import com.danshouseproject.project.moviecatalogue.data.remote.*
import com.danshouseproject.project.moviecatalogue.data.remote.response.*
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.ListFilm

class FakeMovieCatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
) : FilmDataSource {

    companion object {
        private const val ZERO_VAL = 0
        private const val VAL_CONV_SCORE = 10
        private const val INT_60 = 60

        private const val DRAMA_GENRE_STRING = "Drama"
        private const val US_ISO_STRING = "US"
        private const val TVMA_CERTIFICATE_STRING = "TV-MA"
        private const val NULL_CERTIFICATE_STRING = "N"
        private const val TVPG_CERTIFICATE_STRING = "TV-PG"

    }

    private fun handleNullMovieCertificate(): List<MovieCertificate> =
        listOf(
            MovieCertificate(
                US_ISO_STRING,
                listOf(Certificate(TVMA_CERTIFICATE_STRING))
            )
        )

    private fun handleNullCertificate(): List<Certificate> =
        listOf(Certificate(TVMA_CERTIFICATE_STRING))

    private fun handleNullTvCertificate(): List<TvCertificate> =
        listOf(
            TvCertificate(
                NULL_CERTIFICATE_STRING,
                TVPG_CERTIFICATE_STRING
            )
        )

    private fun generateFilmDuration(duration: Int): String {
        val hoursUnit = duration / INT_60
        val minuteUnit = duration % INT_60

        return when (hoursUnit) {
            ZERO_VAL -> "${minuteUnit}m"
            else -> "${hoursUnit}h ${minuteUnit}m"
        }
    }

    private fun convertFilmScore(score: Double): Int =
        (score * VAL_CONV_SCORE).toInt()

    private fun getPosterPathUrl(posterPath: String): String =
        ApiKey.IMAGE_URL + posterPath


    override fun getAllMovies(filmId: Int): LiveData<ListFilm> {
        val listMovies = MutableLiveData<ListFilm>()

        remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
            override fun onMoviesLoaded(
                data: ResponseMovies,
            ) {
                val moviesDuration = generateFilmDuration(data.moviesDuration)
                val moviesScore = convertFilmScore(data.moviesScore)
                val posterPathUrl = getPosterPathUrl(data.moviesPosterPath)
                val instanciateFilm = ListFilm(
                    data.moviesId,
                    data.moviesTitle,
                    moviesDuration,
                    data.moviesReleaseDate,
                    filmOverview = data.moviesOverView,
                    filmImage = posterPathUrl,
                    filmScore = moviesScore,
                )
                listMovies.postValue(instanciateFilm)
            }
        })

        return listMovies
    }


    override fun getAllTvShows(filmId: Int): LiveData<ListFilm> {
        val listTvs = MutableLiveData<ListFilm>()

        remoteDataSource.fetchTvShows(filmId, object : LoadTvResponse {
            override fun onTvShowsLoaded(
                data: ResponseTvShows,
            ) {
                val tvDuration = generateFilmDuration(
                    data.tvDuration?.get(ZERO_VAL)
                        ?: VAL_CONV_SCORE
                )
                val tvScore = convertFilmScore(data.tvScore)
                val posterPathUrl = getPosterPathUrl(data.tvPosterPath)
                val instanciateFilm = ListFilm(
                    data.tvId,
                    data.tvTitle,
                    tvDuration,
                    data.tvReleaseDate,
                    filmOverview = data.tvOverview,
                    filmImage = posterPathUrl,
                    filmScore = tvScore
                )

                listTvs.postValue(instanciateFilm)
            }
        })

        return listTvs
    }


    override fun getMoviesGenres(filmId: Int): LiveData<FilmGenre> {
        val listMoviesGenres = MutableLiveData<FilmGenre>()

        remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
            override fun onMoviesLoaded(data: ResponseMovies) {
                val genre = data.moviesGenres
                    ?: listOf(FetchFilmGenres(DRAMA_GENRE_STRING))
                val listGenres = ArrayList<String>()

                for (idxGenre in genre.indices)
                    listGenres.add(genre[idxGenre].genre)

                listMoviesGenres.postValue(FilmGenre(listGenres, filmId))
            }
        })

        return listMoviesGenres
    }


    override fun getTvGenres(filmId: Int): LiveData<FilmGenre> {
        val listTvGenres = MutableLiveData<FilmGenre>()

        remoteDataSource.fetchTvShows(filmId, object : LoadTvResponse {
            override fun onTvShowsLoaded(data: ResponseTvShows) {
                val genre = data.tvGenres
                    ?: listOf(FetchFilmGenres(DRAMA_GENRE_STRING))
                val listGenres = ArrayList<String>()

                for (idxGenre in genre.indices)
                    listGenres.add(genre[idxGenre].genre)

                listTvGenres.postValue(FilmGenre(listGenres, filmId))
            }
        })

        return listTvGenres
    }


    override fun getMoviesMoreInfo(filmId: Int): LiveData<Pair<String, String>> {
        val isoAndCertifinfo = MutableLiveData<Pair<String, String>>()

        remoteDataSource.fetchMoviesMoreInfo(filmId, object : LoadMoviesMoreInfo {
            override fun onMoviesAdditionInformationReceived(additionalData: ResponseMoviesCertification) {
                var isChanged = false
                val result = additionalData.result ?: handleNullMovieCertificate()

                for (index in result.indices)
                    when {
                        result[index].isoCode == US_ISO_STRING -> {
                            for (itCertif in result[index].moviesCertificate
                                ?: handleNullCertificate()) {
                                if (itCertif.certificate.isEmpty()) continue
                                else {
                                    isoAndCertifinfo.postValue(
                                        Pair(
                                            result[index].isoCode,
                                            itCertif.certificate
                                        )
                                    )
                                    isChanged = true
                                    if (isChanged) break
                                }
                            }
                            break
                        }
                        index == result.lastIndex && !isChanged -> {
                            for (itCertif in result[index].moviesCertificate
                                ?: handleNullCertificate()) {
                                if (itCertif.certificate.isEmpty()) continue
                                else {
                                    isoAndCertifinfo.postValue(
                                        Pair(
                                            result[index].isoCode,
                                            itCertif.certificate
                                        )
                                    )
                                    break
                                }
                            }
                        }
                    }

            }
        })

        return isoAndCertifinfo
    }


    override fun getTvMoreInfo(filmId: Int): LiveData<Pair<String, String>> {
        val isoAndCertifInfo = MutableLiveData<Pair<String, String>>()

        remoteDataSource.fetchTvMoreInfo(filmId, object : LoadTvMoreInfo {
            override fun onTvShowsAdditionInformatonReceived(additionalData: ResponseTvCertification) {
                val result = additionalData.result ?: handleNullTvCertificate()
                ZERO_VAL.let { zeroVal ->
                    isoAndCertifInfo.postValue(
                        Pair(
                            result[zeroVal].isoCode,
                            result[zeroVal].certificate
                        )
                    )
                }
            }
        })

        return isoAndCertifInfo
    }
}