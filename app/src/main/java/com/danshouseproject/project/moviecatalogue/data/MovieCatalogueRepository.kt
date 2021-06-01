package com.danshouseproject.project.moviecatalogue.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danshouseproject.project.moviecatalogue.R
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
        private lateinit var context: Context
        private val rsc = context.resources

        @Volatile
        private var instance: MovieCatalogueRepository? = null

        fun getInstance(remoteData: RemoteDataSource, ctx: Context): MovieCatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: MovieCatalogueRepository(remoteData).apply {
                    context = ctx
                    instance = this
                }
            }

        fun getContext(context: Context) = context.let { ctx -> this.context = ctx }

        private fun handleNullMovieCertificate(): List<MovieCertificate> =
            listOf(
                MovieCertificate(
                    rsc.getString(R.string.iso_alpha2_us),
                    listOf(Certificate(rsc.getString(R.string.film_rating_symbol)))
                )
            )

        private fun handleNullCertificate(): List<Certificate> =
            listOf(Certificate(rsc.getString(R.string.film_rating_symbol)))

        private fun handleNullTvCertificate(): List<TvCertificate> =
            listOf(
                TvCertificate(
                    rsc.getString(R.string.value_for_null_data_fetched_from_api),
                    rsc.getString(R.string.tv_rate_canada_kids_certificate)
                )
            )

    }


    private fun generateFilmDuration(duration: Int): String {
        val hoursUnit = duration / rsc.getInteger(R.integer.score_value60)
        val minuteUnit = duration % rsc.getInteger(R.integer.score_value60)

        return when (hoursUnit) {
            rsc.getInteger(R.integer.zero_value) -> rsc.getString(
                R.string.duration_format_minutes,
                minuteUnit
            )
            else -> rsc.getString(
                R.string.duration_format_hours_minutes,
                hoursUnit,
                minuteUnit
            )
        }
    }

    private fun convertFilmScore(score: Double): Int =
        (score * rsc.getInteger(R.integer.optimal_max_thickness_ratio_size)).toInt()

    private fun getPosterPathUrl(posterPath: String): String =
        ApiKey.IMAGE_URL + posterPath


    override fun getAllMovies(): LiveData<List<ListFilm>> {
        val filmId: List<JsonMoviesId> = remoteDataSource.fetchMoviesId()
            ?: listOf(JsonMoviesId(rsc.getInteger(R.integer.max_score_range)))
        val listMovies = MutableLiveData<List<ListFilm>>()
        val moviesData = ArrayList<ListFilm>()

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
                    moviesData.add(instanciateFilm)
                    listMovies.postValue(moviesData)
                }
            })
        }
        return listMovies
    }


    override fun getAllTvShows(): LiveData<List<ListFilm>> {
        val filmId: List<JsonTvId> = remoteDataSource.fetchTvId() ?: listOf(
            JsonTvId(
                rsc.getInteger(
                    R.integer.max_score_range
                )
            )
        )
        val listTvs = MutableLiveData<List<ListFilm>>()
        val tvsData = ArrayList<ListFilm>()

        for (idxId in filmId.indices) {
            remoteDataSource.fetchTvShows(filmId[idxId].tvShowsId, object : LoadTvResponse {
                override fun onTvShowsLoaded(
                    data: ResponseTvShows,
                ) {
                    val tvDuration = generateFilmDuration(
                        data.tvDuration?.get(rsc.getInteger(R.integer.zero_value))
                            ?: rsc.getInteger(R.integer.optimal_max_thickness_ratio_size)
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
                    tvsData.add(instanciateFilm)
                    listTvs.postValue(tvsData)
                }
            })
        }
        return listTvs
    }


    override fun getMoviesGenres(filmId: Int): LiveData<FilmGenre> {
        val listMoviesGenres = MutableLiveData<FilmGenre>()

        remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
            override fun onMoviesLoaded(data: ResponseMovies) {
                val genre = data.moviesGenres
                    ?: listOf(FetchFilmGenres(rsc.getString(R.string.film_genre_drama)))
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
                    ?: listOf(FetchFilmGenres(rsc.getString(R.string.film_genre_drama)))
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
                        result[index].isoCode == rsc.getString(R.string.iso_alpha2_us) -> {
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
                rsc.getInteger(R.integer.zero_value).let { zeroVal ->
                    isoAndCertifInfo.postValue(Pair(result[zeroVal].isoCode, result[zeroVal].certificate))
                }
            }
        })

        return isoAndCertifInfo
    }

}