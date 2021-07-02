package com.danshouseproject.project.moviecatalogue.di

import android.content.Context
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.data.local.LocalDataSource
import com.danshouseproject.project.moviecatalogue.data.remote.RemoteDataSource
import com.danshouseproject.project.moviecatalogue.db.movie.MovieDatabase
import com.danshouseproject.project.moviecatalogue.helper.RemoteJsonHelper
import com.danshouseproject.project.moviecatalogue.helper.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieCatalogueRepository =
        RemoteDataSource.getInstance(RemoteJsonHelper()).let { remote ->
            MovieDatabase.getDatabase(context).let { db ->
                LocalDataSource.getLocalInstance(db.movieDao()).let { localData ->
                    AppExecutors().let { executors ->
                        with(MovieCatalogueRepository) {
                            getInstance(remote, context, executors, localData)
                        }
                    }
                }
            }
        }
}