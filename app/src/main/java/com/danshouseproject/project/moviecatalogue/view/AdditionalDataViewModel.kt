package com.danshouseproject.project.moviecatalogue.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.data.vo.Resource

class AdditionalDataViewModel(private val mMovieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getMoviesAdditionalData(filmId: Int): LiveData<Resource<FilmInfo>> =
        mMovieCatalogueRepository.getMoviesMoreInfo(filmId)

    fun getTvAdditionalData(filmId: Int): LiveData<Resource<FilmInfo>> =
        mMovieCatalogueRepository.getTvMoreInfo(filmId)

    fun getOrderedFilm(sort: String): LiveData<PagedList<FavoriteFilm>> =
        mMovieCatalogueRepository.getOrderedFilm(sort)

    fun getFavoriteFilm(isMovies: Boolean): LiveData<PagedList<FavoriteFilm>> =
        mMovieCatalogueRepository.getAllFavoriteFilm(isMovies)

    fun addToFavorite(film: ListFilm) {
        val instance = FavoriteFilm(
            filmId = film.filmId,
            filmName = film.filmName,
            posterPath = film.filmImage,
            overview = film.filmOverview,
            filmScore = film.filmScore,
            releaseDate = film.filmReleaseDate,
            duration = film.filmDuration,
            isMovies = film.isMovies
        )
        mMovieCatalogueRepository.addToFavorite(instance)
    }

    fun removeFromFavorite(filmId: Int) =
        mMovieCatalogueRepository.removeFromFavorite(filmId)

    fun checkIsFavorite(filmId: Int): LiveData<Int> =
        mMovieCatalogueRepository.checkIsFavorite(filmId)

}