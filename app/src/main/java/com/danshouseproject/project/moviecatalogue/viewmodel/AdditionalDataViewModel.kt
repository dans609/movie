package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.vo.Resource

class AdditionalDataViewModel(private val mMovieCatalogueRepository: MovieCatalogueRepository) :
    ViewModel() {

    fun getMoviesAdditionalData(filmId: Int): LiveData<Resource<FilmInfo>> =
        mMovieCatalogueRepository.getMoviesMoreInfo(filmId)

    fun getTvAdditionalData(filmId: Int): LiveData<Resource<FilmInfo>> =
        mMovieCatalogueRepository.getTvMoreInfo(filmId)

    fun getFavoriteFilm(isMovies: Boolean): LiveData<List<FavoriteFilm>> =
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