package com.danshouseproject.project.moviecatalogue.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danshouseproject.project.moviecatalogue.api.tmdb.ApiKey
import com.danshouseproject.project.moviecatalogue.data.remote.*
import com.danshouseproject.project.moviecatalogue.data.remote.response.*
import com.danshouseproject.project.moviecatalogue.data.remote.response.json.JsonMoviesId
import com.danshouseproject.project.moviecatalogue.data.remote.response.json.JsonTvId
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.ListFilm

class MovieCatalogueRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    FilmDataSource {

    companion object {
        @Volatile
        private var instance: MovieCatalogueRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MovieCatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: MovieCatalogueRepository(remoteData).apply {
                    instance = this
                }
            }

        private fun handleNullMovieCertificate(): List<MovieCertificate> =
            listOf(MovieCertificate("US", listOf(Certificate("TV-MA"))))

        private fun handleNullCertificate(): List<Certificate> =
            listOf(Certificate("TV-MA"))

        private fun handleNullTvCertificate(): List<TvCertificate> =
            listOf(
                TvCertificate(
                    "N",
                    "TV-PG"
                )
            )

    }


    private fun generateFilmDuration(duration: Int): String {
        val hoursUnit = duration / 60
        val minuteUnit = duration % 60

        return when (hoursUnit) {
            0 -> "${minuteUnit}m"
            else -> "${hoursUnit}h ${minuteUnit}m"
        }
    }

    private fun convertFilmScore(score: Double): Int =
        (score * 10).toInt()

    private fun getPosterPathUrl(posterPath: String): String =
        ApiKey.IMAGE_URL + posterPath


    override fun getAllMovies(): LiveData<List<ListFilm>> {
        val filmId: List<JsonMoviesId> = remoteDataSource.fetchMoviesId()
            ?: listOf(JsonMoviesId(100))
        val listData = ArrayList<ListFilm>()
        val listMovies = MutableLiveData<List<ListFilm>>()

        for (idxId in filmId.indices) {
            remoteDataSource.fetchMovies(filmId[idxId].moviesId, object : LoadMoviesResponse {
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
                    listData.add(instanciateFilm)
                }
            })
        }
        listMovies.postValue(listData)
        return listMovies
    }


    override fun getAllTvShows(): LiveData<List<ListFilm>> {
        val filmId: List<JsonTvId> = remoteDataSource.fetchTvId() ?: listOf(
            JsonTvId(
                100
            )
        )
        val listTvs = MutableLiveData<List<ListFilm>>()
        val listData = ArrayList<ListFilm>()

        for (idxId in filmId.indices) {
            remoteDataSource.fetchTvShows(filmId[idxId].tvShowsId, object : LoadTvResponse {
                override fun onTvShowsLoaded(
                    data: ResponseTvShows,
                ) {
                    val tvDuration = generateFilmDuration(
                        data.tvDuration?.get(0)
                            ?: 10
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
                    listData.add(instanciateFilm)
                }
            })
        }
        listTvs.postValue(listData)
        return listTvs
    }


    override fun getMoviesGenres(filmId: Int): LiveData<FilmGenre> {
        val listMoviesGenres = MutableLiveData<FilmGenre>()

        remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
            override fun onMoviesLoaded(data: ResponseMovies) {
                val genre = data.moviesGenres
                    ?: listOf(FetchFilmGenres("Drama"))
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
                    ?: listOf(FetchFilmGenres("Drama"))
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
                        result[index].isoCode == "US" -> {
                            for (itCertif in result[index].moviesCertificate
                                ?: handleNullCertificate()) {
                                if (itCertif.certificate.isEmpty()) continue
                                else {
                                    isoAndCertifinfo.postValue(Pair(result[index].isoCode, itCertif.certificate))
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
                                    isoAndCertifinfo.postValue(Pair(result[index].isoCode, itCertif.certificate))
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
                0.let { zeroVal ->
                    isoAndCertifInfo.postValue(Pair(result[zeroVal].isoCode, result[zeroVal].certificate))
                }
            }
        })

        return isoAndCertifInfo
    }

}