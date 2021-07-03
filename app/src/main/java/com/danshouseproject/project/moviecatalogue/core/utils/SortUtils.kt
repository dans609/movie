package com.danshouseproject.project.moviecatalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    private const val QUERY_SORT = "ORDER BY"
    const val RANDOM = "Random"
    const val HIGH_RANK = "Highest Score"
    const val LOW_RANK = "Lowest Score"
    const val LONGEST_RUNTIME = "Longest Duration"
    const val FASTEST_RUNTIME = "Fastest Duration"
    const val SORT_ID_ASC = "sort_id_asc"
    const val SORT_ID_DES = "sort_id_desc"

    fun getSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM favorite_film ")
        when (filter) {
            HIGH_RANK -> simpleQuery.append("$QUERY_SORT filmScore DESC")
            LOW_RANK -> simpleQuery.append("$QUERY_SORT filmScore ASC")
            RANDOM -> simpleQuery.append("$QUERY_SORT RANDOM()")
            LONGEST_RUNTIME -> simpleQuery.append("$QUERY_SORT duration DESC")
            FASTEST_RUNTIME -> simpleQuery.append("$QUERY_SORT duration ASC")
            SORT_ID_ASC -> simpleQuery.append("$QUERY_SORT id ASC")
            SORT_ID_DES -> simpleQuery.append("$QUERY_SORT id DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}