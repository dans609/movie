package com.danshouseproject.project.moviecatalogue.di

import android.content.Context
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.data.remote.RemoteDataSource
import com.danshouseproject.project.moviecatalogue.helper.RemoteJsonHelper

object Injection {

    fun provideRepository(context: Context): MovieCatalogueRepository =
        RemoteDataSource.getInstance(RemoteJsonHelper()).let { remote ->
            MovieCatalogueRepository.getInstance(remote, context)
        }

}