package com.danshouseproject.project.moviecatalogue.core.utils.helper

import androidx.room.TypeConverter
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.GenreConverter
import com.google.gson.Gson

class GenreTypeConverter {

    @TypeConverter
    fun listToJson(value: List<GenreConverter>): String =
        Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<GenreConverter>::class.java).toList()

}