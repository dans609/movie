package com.danshouseproject.project.moviecatalogue.core.di

import android.content.Context
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.data.local.LocalDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.RemoteDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.local.lib.room.db.movie.MovieDatabase
import com.danshouseproject.project.moviecatalogue.core.utils.helper.RemoteJsonHelper
import com.danshouseproject.project.moviecatalogue.core.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieCatalogueRepository =
        RemoteDataSource.getInstance(RemoteJsonHelper()).let { remote ->
            MovieDatabase.getDatabase(context).let { db ->
                LocalDataSource.getLocalInstance(db.movieDao()).let { localData ->
                    AppExecutors().let { executors ->
                        with(MovieCatalogueRepository) {
                            getInstance(remote, executors, localData)
                        }
                    }
                }
            }
        }
}