package com.danshouseproject.project.moviecatalogue.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.di.Injection
import com.danshouseproject.project.moviecatalogue.viewmodel.GenreViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.TvShowsViewModel

class ViewModelFactory private constructor(private val mMovieCatalogueRepository: MovieCatalogueRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        private const val UNKNOWN_VIEWMODEL = "Unknown ViewModel Class"
        private const val UNCHECKED_CAST = "UNCHECKED_CAST"

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) ->
                MoviesViewModel(mMovieCatalogueRepository) as T
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) ->
                TvShowsViewModel(mMovieCatalogueRepository) as T
            modelClass.isAssignableFrom(GenreViewModel::class.java) ->
                GenreViewModel(mMovieCatalogueRepository) as T
            else -> throw Throwable("$UNKNOWN_VIEWMODEL ${modelClass.name}")
        }

    }

}