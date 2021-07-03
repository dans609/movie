package com.danshouseproject.project.moviecatalogue.core.components.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.di.Injection
import com.danshouseproject.project.moviecatalogue.view.AdditionalDataViewModel
import com.danshouseproject.project.moviecatalogue.view.detail.GenreViewModel
import com.danshouseproject.project.moviecatalogue.view.home.branch.movies.MoviesViewModel
import com.danshouseproject.project.moviecatalogue.view.home.branch.tv.TvShowsViewModel

class ViewModelFactory private constructor(private val mMovieCatalogueRepository: MovieCatalogueRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        private const val UNKNOWN_VIEWMODEL = "Unknown ViewModel Class"

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        mMovieCatalogueRepository.let { repo ->
            return when {
                modelClass.isAssignableFrom(MoviesViewModel::class.java) ->
                    MoviesViewModel(repo) as T
                modelClass.isAssignableFrom(TvShowsViewModel::class.java) ->
                    TvShowsViewModel(repo) as T
                modelClass.isAssignableFrom(GenreViewModel::class.java) ->
                    GenreViewModel(repo) as T
                modelClass.isAssignableFrom(AdditionalDataViewModel::class.java) ->
                    AdditionalDataViewModel(repo) as T
                else -> throw Throwable("$UNKNOWN_VIEWMODEL ${modelClass.name}")
            }
        }
}