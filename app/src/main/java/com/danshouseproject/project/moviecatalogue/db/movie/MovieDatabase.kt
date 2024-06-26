package com.danshouseproject.project.moviecatalogue.db.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danshouseproject.project.moviecatalogue.helper.utils.GenreTypeConverter
import com.danshouseproject.project.moviecatalogue.model.*

@Database(
    entities = [ListFilm::class,
        FilmGenre::class,
        FilmInfo::class,
        GenreConverter::class,
        FavoriteFilm::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
            }
    }
}