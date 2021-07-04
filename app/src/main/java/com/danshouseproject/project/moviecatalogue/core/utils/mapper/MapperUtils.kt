package com.danshouseproject.project.moviecatalogue.core.utils.mapper

import androidx.paging.PagedList
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.lib.retrofit.api.tmdb.ApiKey

object MapperUtils {
    private const val EMPTY = 0
    private const val INITIAL_VAL = 4
    private const val ROUND = 10
    private const val HOURS = 60

    private fun stringBuilder(text: String): String = StringBuilder().append(text).toString()

    private fun duration(hoursUnit: Int, minuteUnit: Int): String =
        when (hoursUnit) {
            EMPTY -> stringBuilder("${minuteUnit}m")
            else -> stringBuilder("${hoursUnit}h ${minuteUnit}m")
        }

    fun convertDuration(duration: Int): String {
        val hoursUnit = duration / HOURS
        val minuteUnit = duration % HOURS

        return duration(hoursUnit, minuteUnit)
    }

    fun convertDuration(duration: List<Int>?): String {
        val runtime = duration?.get(EMPTY) ?: EMPTY
        val hoursUnit = runtime / HOURS
        val minuteUnit = runtime % HOURS

        return duration(hoursUnit, minuteUnit)
    }

    fun convertScore(score: Double): Int = (score * ROUND).toInt()

    fun combinePath(posterPath: String): String = ApiKey.IMAGE_URL + posterPath

    fun pagedListConfig(): PagedList.Config =
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(INITIAL_VAL)
            .setPageSize(INITIAL_VAL)
            .build()
}