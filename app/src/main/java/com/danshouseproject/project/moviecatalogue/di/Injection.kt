package com.danshouseproject.project.moviecatalogue.di

import android.content.Context
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.data.remote.RemoteDataSource
import com.danshouseproject.project.moviecatalogue.helper.RemoteJsonHelper

object Injection {

    fun provideRepository(context: Context): MovieCatalogueRepository {
        val remote = RemoteDataSource.getInstance(RemoteJsonHelper(context))
        return MovieCatalogueRepository.getInstance(remote, context)
    }

}