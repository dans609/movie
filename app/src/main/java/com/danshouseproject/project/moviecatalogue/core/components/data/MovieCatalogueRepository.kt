package com.danshouseproject.project.moviecatalogue.core.components.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.core.components.data.local.LocalDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.*
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMoviesCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvCertification
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.status.ApiResponse
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.repository.FilmDataSource
import com.danshouseproject.project.moviecatalogue.core.utils.AppExecutors
import com.danshouseproject.project.moviecatalogue.core.utils.mapper.MapperUtils
import com.danshouseproject.project.moviecatalogue.core.utils.mapper.entities.DataMapper

class MovieCatalogueRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource,
    private var appExecutors: AppExecutors
) :
    FilmDataSource {

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
                DataMapper.mapResponseToEntity(DataMapper.EXTRA_MOVIES, data).let { entity ->
                    if (entity != null) localDataSource.insertFilm(entity)
                }
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
                DataMapper.mapResponseToEntity(DataMapper.EXTRA_TV, data).let { entity ->
                    if (entity != null) localDataSource.insertFilm(entity)
                }
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
                DataMapper.mapResponseGenresToEntity(DataMapper.EXTRA_MOVIES, data).let { entity ->
                    if (entity != null) localDataSource.insertFilmGenres(entity)
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
                DataMapper.mapResponseGenresToEntity(DataMapper.EXTRA_TV, data).let { entity ->
                    if (entity != null) localDataSource.insertFilmGenres(entity)
                }
            }
        }.asLiveData
    }


    override fun getMoviesMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>> {
        return object : NetworkBoundResource<FilmInfo, ResponseMoviesCertification>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmInfo> =
                localDataSource.getFilmInfo(filmId, IS_MOVIES)

            override fun shouldFetch(data: FilmInfo?): Boolean =
                data == null || data.filmId == EMPTY

            override fun createCall(): LiveData<ApiResponse<ResponseMoviesCertification>> {
                val movieInfo = MutableLiveData<ApiResponse<ResponseMoviesCertification>>()

                remoteDataSource.fetchMoviesMoreInfo(filmId, object : LoadMoviesMoreInfo {
                    override fun onMoviesAdditionInformationReceived(
                        additionalData: ApiResponse<ResponseMoviesCertification>
                    ) = movieInfo.postValue(additionalData)
                })

                return movieInfo
            }

            override fun saveCallResult(data: ResponseMoviesCertification) {
                DataMapper.mapResponseInfoToEntity(DataMapper.EXTRA_MOVIE_INFO, data, filmId).let {
                    if (it != null) localDataSource.insertFilmInfo(it)
                }
            }
        }.asLiveData
    }


    override fun getTvMoreInfo(filmId: Int): LiveData<Resource<FilmInfo>> {
        return object : NetworkBoundResource<FilmInfo, ResponseTvCertification>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmInfo> =
                localDataSource.getFilmInfo(filmId, !IS_MOVIES)

            override fun shouldFetch(data: FilmInfo?): Boolean =
                data == null || data.filmId == EMPTY

            override fun createCall(): LiveData<ApiResponse<ResponseTvCertification>> {
                val tvMoreInfo = MutableLiveData<ApiResponse<ResponseTvCertification>>()

                remoteDataSource.fetchTvMoreInfo(filmId, object : LoadTvMoreInfo {
                    override fun onTvShowsAdditionInformatonReceived(
                        additionalData: ApiResponse<ResponseTvCertification>
                    ) = tvMoreInfo.postValue(additionalData)
                })

                return tvMoreInfo
            }

            override fun saveCallResult(data: ResponseTvCertification) {
                DataMapper.mapResponseInfoToEntity(DataMapper.EXTRA_TV_INFO, data, filmId).let {
                    if (it != null) localDataSource.insertFilmInfo(it)
                }
            }
        }.asLiveData
    }

    override fun addToFavorite(favoriteFilm: FavoriteFilm) {
        appExecutors.diskIO.execute { localDataSource.addToFavorite(favoriteFilm) }
    }

    override fun removeFromFavorite(filmId: Int) {
        appExecutors.diskIO.execute { localDataSource.removeFromFavorite(filmId) }
    }

    override fun checkIsFavorite(filmId: Int): LiveData<Int> {
        val id = MutableLiveData<Int>()
        appExecutors.diskIO.execute { id.postValue(localDataSource.checkIsFavorite(filmId)) }
        return id
    }

    override fun getAllFavoriteFilm(isMovies: Boolean): LiveData<PagedList<FavoriteFilm>> =
        MapperUtils.pagedListConfig().let { config ->
            LivePagedListBuilder(localDataSource.getAllFavoriteFilm(isMovies), config).build()
        }

    override fun getOrderedFilm(sort: String): LiveData<PagedList<FavoriteFilm>> =
        MapperUtils.pagedListConfig().let { config ->
            LivePagedListBuilder(localDataSource.getOrderedFilm(sort), config).build()
        }


    companion object {
        @Volatile
        private var instance: MovieCatalogueRepository? = null
        private const val IS_MOVIES = true
        private const val EMPTY = 0

        fun getInstance(
            remoteData: RemoteDataSource,
            executors: AppExecutors,
            localData: LocalDataSource
        ): MovieCatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: MovieCatalogueRepository(remoteData, localData, executors).apply {
                    instance = this
                }
            }
    }
}