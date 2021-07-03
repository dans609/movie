package com.danshouseproject.project.moviecatalogue.core.components.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit.api.tmdb.ApiKey
import com.danshouseproject.project.moviecatalogue.core.components.data.local.LocalDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.*
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.*
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.status.ApiResponse
import com.danshouseproject.project.moviecatalogue.core.utils.AppExecutors
import com.danshouseproject.project.moviecatalogue.core.components.data.vo.Resource
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.*
import com.danshouseproject.project.moviecatalogue.core.components.domain.repository.FilmDataSource

class FakeMovieCatalogueRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource,
    private var appExecutors: AppExecutors
) :
    FilmDataSource {

    private fun handleNullMovieCertificate(): List<MovieCertificate> =
        listOf(MovieCertificate(US_ISO, listOf(Certificate(TVMA_RATING))))

    private fun handleNullCertificate(): List<Certificate> =
        listOf(Certificate(TVMA_RATING))

    private fun handleNullTvCertificate(): List<TvCertificate> =
        listOf(TvCertificate(NULL_ISO, TVPG_RATING))

    fun generateFilmDuration(duration: Int): String {
        val hoursUnit = duration / ONE_HOURS
        val minuteUnit = duration % ONE_HOURS

        return when (hoursUnit) {
            ZERO_VAL -> "${minuteUnit}m"
            else -> "${hoursUnit}h ${minuteUnit}m"
        }
    }

    fun convertFilmScore(score: Double): Int =
        (score * TEEN_VAL).toInt()

    private fun getPosterPathUrl(posterPath: String): String =
        ApiKey.IMAGE_URL + posterPath


    override fun getAllMovies(filmId: Int): LiveData<Resource<ListFilm>> {
        return object : NetworkBoundResource<ListFilm, ResponseMovies>(appExecutors) {
            override fun loadFromDB(): LiveData<ListFilm> =
                localDataSource.getFilm(filmId, IS_MOVIES)

            override fun shouldFetch(data: ListFilm?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResponseMovies>> {
                val result = MutableLiveData<ApiResponse<ResponseMovies>>()
                remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
                    override fun onMoviesLoaded(data: ApiResponse<ResponseMovies>) =
                        result.postValue(data)
                })
                return result
            }

            override fun saveCallResult(data: ResponseMovies) {
                val moviesDuration = generateFilmDuration(data.moviesDuration)
                val moviesScore = convertFilmScore(data.moviesScore)
                val posterPathUrl = getPosterPathUrl(data.moviesPosterPath)
                val instanciateFilm = ListFilm(
                    filmId = data.moviesId,
                    filmName = data.moviesTitle,
                    filmDuration = moviesDuration,
                    filmReleaseDate = data.moviesReleaseDate,
                    filmOverview = data.moviesOverView,
                    filmImage = posterPathUrl,
                    filmScore = moviesScore,
                    isMovies = true
                )

                localDataSource.insertFilm(instanciateFilm)
            }
        }.asLiveData
    }


    override fun getAllTvShows(filmId: Int): LiveData<Resource<ListFilm>> {
        return object : NetworkBoundResource<ListFilm, ResponseTvShows>(appExecutors) {
            override fun loadFromDB(): LiveData<ListFilm> =
                localDataSource.getFilm(filmId, !IS_MOVIES)

            override fun shouldFetch(data: ListFilm?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResponseTvShows>> {
                val list = MutableLiveData<ApiResponse<ResponseTvShows>>()
                remoteDataSource.fetchTvShows(filmId, object : LoadTvResponse {
                    override fun onTvShowsLoaded(data: ApiResponse<ResponseTvShows>) =
                        list.postValue(data)
                })
                return list
            }

            override fun saveCallResult(data: ResponseTvShows) {
                val tvDuration = generateFilmDuration(data.tvDuration?.get(ZERO_VAL) ?: TEEN_VAL)
                val tvScore = convertFilmScore(data.tvScore)
                val posterPathUrl = getPosterPathUrl(data.tvPosterPath)
                val instanciateFilm = ListFilm(
                    filmId = data.tvId,
                    filmName = data.tvTitle,
                    filmDuration = tvDuration,
                    filmReleaseDate = data.tvReleaseDate,
                    filmOverview = data.tvOverview,
                    filmImage = posterPathUrl,
                    filmScore = tvScore
                )

                localDataSource.insertFilm(instanciateFilm)
            }
        }.asLiveData
    }


    override fun getMoviesGenres(filmId: Int): LiveData<Resource<FilmGenre>> {
        return object : NetworkBoundResource<FilmGenre, ResponseMovies>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmGenre> =
                localDataSource.getFilmGenre(filmId, IS_MOVIES)

            override fun shouldFetch(data: FilmGenre?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResponseMovies>> {
                val list = MutableLiveData<ApiResponse<ResponseMovies>>()
                remoteDataSource.fetchMovies(filmId, object : LoadMoviesResponse {
                    override fun onMoviesLoaded(data: ApiResponse<ResponseMovies>) =
                        list.postValue(data)
                })
                return list
            }

            override fun saveCallResult(data: ResponseMovies) {
                val genre = data.moviesGenres ?: listOf(FetchFilmGenres(GENRE_DRAMA))
                val listGenre = ArrayList<GenreConverter>()

                for (idxGenre in genre.indices)
                    listGenre.add(GenreConverter(filmId = filmId, genre = genre[idxGenre].genre))

                FilmGenre(filmId = filmId, genre = listGenre, isMovies = true).let { instance ->
                    localDataSource.insertFilmGenres(instance)
                }
            }
        }.asLiveData
    }


    override fun getTvGenres(filmId: Int): LiveData<Resource<FilmGenre>> {
        return object : NetworkBoundResource<FilmGenre, ResponseTvShows>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmGenre> =
                localDataSource.getFilmGenre(filmId, !IS_MOVIES)

            override fun shouldFetch(data: FilmGenre?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<ResponseTvShows>> {
                val list = MutableLiveData<ApiResponse<ResponseTvShows>>()
                remoteDataSource.fetchTvShows(filmId, object : LoadTvResponse {
                    override fun onTvShowsLoaded(data: ApiResponse<ResponseTvShows>) =
                        list.postValue(data)
                })
                return list
            }

            override fun saveCallResult(data: ResponseTvShows) {
                val genre = data.tvGenres ?: listOf(FetchFilmGenres(GENRE_DRAMA))
                val listGenre = ArrayList<GenreConverter>()

                for (idxGenre in genre.indices)
                    listGenre.add(GenreConverter(filmId = filmId, genre = genre[idxGenre].genre))

                FilmGenre(filmId = filmId, genre = listGenre).let { instance ->
                    localDataSource.insertFilmGenres(instance)
                }
            }
        }.asLiveData
    }


    override fun getMoviesMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>> {
        return object : NetworkBoundResource<FilmInfo, ResponseMoviesCertification>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmInfo> =
                localDataSource.getFilmInfo(filmId, IS_MOVIES)

            override fun shouldFetch(data: FilmInfo?): Boolean =
                data == null || data.filmId == ZERO_VAL

            override fun createCall(): LiveData<ApiResponse<ResponseMoviesCertification>> {
                val movieInfo = MutableLiveData<ApiResponse<ResponseMoviesCertification>>()
                remoteDataSource.fetchMoviesMoreInfo(filmId, object : LoadMoviesMoreInfo {
                    override fun onMoviesAdditionInformationReceived(additionalData: ApiResponse<ResponseMoviesCertification>) =
                        movieInfo.postValue(additionalData)
                })
                return movieInfo
            }

            override fun saveCallResult(data: ResponseMoviesCertification) {
                val isDataFound = MutableLiveData<Boolean>()
                isDataFound.postValue(false)
                val result = data.result ?: handleNullMovieCertificate()

                for (index in result.indices)
                    when {
                        result[index].isoCode == US_ISO -> {
                            for (certif in result[index].moviesCertificate
                                ?: handleNullCertificate()) {
                                if (certif.certificate.isEmpty()) continue
                                else {
                                    val instance = FilmInfo(
                                        filmId = filmId,
                                        isoCode = result[index].isoCode,
                                        filmRating = certif.certificate,
                                        isMovies = IS_MOVIES
                                    )

                                    localDataSource.insertFilmInfo(instance)
                                    isDataFound.postValue(true)
                                    break
                                }
                            }
                            break
                        }
                        index == result.lastIndex && !(isDataFound.value ?: true) -> {
                            for (certif in result[index].moviesCertificate
                                ?: handleNullCertificate()) {
                                if (certif.certificate.isEmpty()) continue
                                else {
                                    val instance = FilmInfo(
                                        filmId = filmId,
                                        isoCode = result[index].isoCode,
                                        filmRating = certif.certificate,
                                        isMovies = IS_MOVIES
                                    )
                                    localDataSource.insertFilmInfo(instance)
                                    break
                                }
                            }
                        }
                    }
            }
        }.asLiveData
    }


    override fun getTvMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>> {
        return object : NetworkBoundResource<FilmInfo, ResponseTvCertification>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmInfo> =
                localDataSource.getFilmInfo(filmId, !IS_MOVIES)

            override fun shouldFetch(data: FilmInfo?): Boolean =
                data == null || data.filmId == ZERO_VAL

            override fun createCall(): LiveData<ApiResponse<ResponseTvCertification>> {
                val tvMoreInfo = MutableLiveData<ApiResponse<ResponseTvCertification>>()
                remoteDataSource.fetchTvMoreInfo(filmId, object : LoadTvMoreInfo {
                    override fun onTvShowsAdditionInformatonReceived(additionalData: ApiResponse<ResponseTvCertification>) {
                        tvMoreInfo.postValue(additionalData)
                    }
                })
                return tvMoreInfo
            }

            override fun saveCallResult(data: ResponseTvCertification) {
                val result = data.result ?: handleNullTvCertificate()
                ZERO_VAL.let { zeroVal ->
                    val instance = FilmInfo(
                        filmId = filmId,
                        isoCode = result[zeroVal].isoCode,
                        filmRating = result[zeroVal].certificate
                    )

                    localDataSource.insertFilmInfo(instance)
                }
            }
        }.asLiveData
    }


    override fun addToFavorite(favoriteFilm: FavoriteFilm) =
        appExecutors.diskIO.execute { localDataSource.addToFavorite(favoriteFilm) }

    override fun removeFromFavorite(filmId: Int) =
        appExecutors.diskIO.execute { localDataSource.removeFromFavorite(filmId) }

    override fun checkIsFavorite(filmId: Int): LiveData<Int> {
        val id = MutableLiveData<Int>()
        appExecutors.diskIO.execute {
            id.postValue(localDataSource.checkIsFavorite(filmId))
        }
        return id
    }

    override fun getAllFavoriteFilm(isMovies: Boolean): LiveData<PagedList<FavoriteFilm>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getAllFavoriteFilm(isMovies), config).build()
    }


    companion object {
        private const val IS_MOVIES = true
        private const val ZERO_VAL = 0
        private const val TEEN_VAL = 10
        private const val ONE_HOURS = 60
        private const val US_ISO = "US"
        private const val NULL_ISO = "N"
        private const val TVMA_RATING = "TV-MA"
        private const val TVPG_RATING = "TV-PG"
        private const val GENRE_DRAMA = "Drama"
    }
}