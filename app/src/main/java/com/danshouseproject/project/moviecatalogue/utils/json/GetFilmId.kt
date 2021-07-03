package com.danshouseproject.project.moviecatalogue.utils.json

import android.content.Context
import com.danshouseproject.project.moviecatalogue.R
import org.json.JSONException
import org.json.JSONObject

class GetFilmId(private val context: Context) {

    private fun parseFileToString(fileName: String): String? =
        context.assets.open(fileName).let { `is` ->
            try {
                val buffer = ByteArray(`is`.available())
                `is`.read(buffer)
                `is`.close()
                String(buffer)
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            } finally {
                `is`.close()
            }
        }


    fun fetchMoviesId(): List<Int>? {
        val moviesId = ArrayList<Int>()

        return try {
            val responseObject = JSONObject(parseFileToString("filmid.json").toString())
            val responseArray = responseObject.getJSONArray("movies_id")

            for (index in context.resources.getInteger(R.integer.zero_value) until responseArray.length()) {
                val responseMovie = responseArray.getJSONObject(index)
                val id = responseMovie.getInt("id")

                moviesId.add(id)
            }

            moviesId
        } catch (exception: JSONException) {
            exception.printStackTrace()
            null
        }
    }


    fun fetchTvShowsId(): List<Int>? {
        val tvId = ArrayList<Int>()

        return try {
            val responseObject = JSONObject(parseFileToString("filmid.json").toString())
            val responseArray = responseObject.getJSONArray("tv_shows_id")

            for (index in context.resources.getInteger(R.integer.zero_value) until responseArray.length()) {
                val responseTv = responseArray.getJSONObject(index)
                val id = responseTv.getInt("id")

                tvId.add(id)
            }

            tvId
        } catch (exception: JSONException) {
            exception.printStackTrace()
            null
        }
    }

}