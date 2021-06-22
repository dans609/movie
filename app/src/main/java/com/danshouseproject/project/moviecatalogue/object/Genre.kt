package com.danshouseproject.project.moviecatalogue.`object`

import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.helper.ConvertTypeHelper.convertListIntToListString
import com.danshouseproject.project.moviecatalogue.model.FilmGenre

object Genre {

    private val moviesId: List<Int> = listOf(
        R.integer.zero_value, R.integer.int_1, R.integer.int_2,
        R.integer.int_3, R.integer.int_4, R.integer.int_5,
        R.integer.int_6, R.integer.int_7, R.integer.int_8,
        R.integer.int_9, R.integer.optimal_max_thickness_ratio_size, R.integer.int_11,
        R.integer.int_12, R.integer.int_13, R.integer.int_14,
        R.integer.int_15, R.integer.int_16, R.integer.int_17,
        R.integer.int_18
    )

    private val moviesGenreList: List<List<String>> = listOf(
        convertListIntToListString(
            R.string.film_genre_drama,
            R.string.film_genre_romance,
            R.string.film_genre_music
        ),
        convertListIntToListString(
            R.string.film_genre_action,
            R.string.film_genre_scific,
            R.string.film_genre_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_action,
            R.string.film_genre_adventure,
            R.string.film_genre_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_music,
            R.string.film_genre_drama,
            R.string.film_genre_history
        ),
        convertListIntToListString(
            R.string.film_genre_action,
            R.string.film_genre_crime,
            R.string.film_genre_thriller
        ),
        convertListIntToListString(R.string.film_genre_drama),
        convertListIntToListString(
            R.string.film_genre_adventure,
            R.string.film_genre_fantasy,
            R.string.film_genre_drama
        ),
        convertListIntToListString(
            R.string.film_genre_thriller,
            R.string.film_genre_drama,
            R.string.film_genre_scific
        ),
        convertListIntToListString(
            R.string.film_genre_animation,
            R.string.film_genre_family,
            R.string.film_genre_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_adventure,
            R.string.film_genre_action,
            R.string.film_genre_scific
        ),
        convertListIntToListString(R.string.film_genre_drama, R.string.film_genre_history),
        convertListIntToListString(R.string.film_genre_action),
        convertListIntToListString(R.string.film_genre_adventure, R.string.film_genre_scific),
        convertListIntToListString(
            R.string.film_genre_horror,
            R.string.film_genre_war,
            R.string.film_genre_scific
        ),
        convertListIntToListString(
            R.string.film_genre_family,
            R.string.film_genre_animation,
            R.string.film_genre_comedy,
            R.string.film_genre_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_adventure,
            R.string.film_genre_action,
            R.string.film_genre_thriller
        ),
        convertListIntToListString(
            R.string.film_genre_thriller,
            R.string.film_genre_mystery,
            R.string.film_genre_drama
        ),
        convertListIntToListString(
            R.string.film_genre_action,
            R.string.film_genre_adventure,
            R.string.film_genre_animation,
            R.string.film_genre_scific,
            R.string.film_genre_comedy
        ),
        convertListIntToListString(
            R.string.film_genre_war,
            R.string.film_genre_action,
            R.string.film_genre_drama,
            R.string.film_genre_history
        )
    )

    private val tvShowsGenreList: List<List<String>> = listOf(
        convertListIntToListString(
            R.string.film_genre_crime,
            R.string.film_genre_mystery,
            R.string.film_genre_action_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_scific_fantasy,
            R.string.film_genre_comedy,
            R.string.film_genre_drama
        ),
        convertListIntToListString(
            R.string.film_genre_animation,
            R.string.film_genre_action_adventure,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_action_adventure,
            R.string.film_genre_animation,
            R.string.film_genre_comedy,
            R.string.film_genre_scific_fantasy,
            R.string.film_genre_mystery
        ),
        convertListIntToListString(R.string.film_genre_animation, R.string.film_genre_comedy),
        convertListIntToListString(
            R.string.film_genre_action_adventure,
            R.string.film_genre_crime,
            R.string.film_genre_drama,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_scific_fantasy,
            R.string.film_genre_drama,
            R.string.film_genre_action_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_drama,
            R.string.film_genre_crime,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(R.string.film_genre_drama),
        convertListIntToListString(R.string.film_genre_action_adventure, R.string.film_genre_drama),
        convertListIntToListString(
            R.string.film_genre_action_adventure,
            R.string.film_genre_drama,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_animation,
            R.string.film_genre_action_adventure,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_crime,
            R.string.film_genre_action_adventure,
            R.string.film_genre_drama
        ),
        convertListIntToListString(
            R.string.film_genre_mystery,
            R.string.film_genre_drama,
            R.string.film_genre_crime
        ),
        convertListIntToListString(R.string.film_genre_comedy, R.string.film_genre_drama),
        convertListIntToListString(
            R.string.film_genre_drama,
            R.string.film_genre_scific_fantasy,
            R.string.film_genre_action_adventure
        ),
        convertListIntToListString(
            R.string.film_genre_drama,
            R.string.film_genre_mystery,
            R.string.film_genre_scific_fantasy
        ),
        convertListIntToListString(
            R.string.film_genre_family,
            R.string.film_genre_animation,
            R.string.film_genre_comedy
        ),
        convertListIntToListString(
            R.string.film_genre_action_adventure,
            R.string.film_genre_scific_fantasy,
            R.string.film_genre_drama
        )
    )

    fun generateMoviesGenre(): List<FilmGenre> {
        val listGenre = ArrayList<FilmGenre>()
        listGenre.clear()

        for (index in moviesGenreList.indices) {
            val filmGenre = FilmGenre()
            // filmGenre.genre = moviesGenreList[index]
            filmGenre.filmId = moviesId[index]
            listGenre.add(filmGenre)
        }
        return listGenre
    }

    fun generateTvShowsGenre(): List<FilmGenre> {
        val listGenre = ArrayList<FilmGenre>()
        listGenre.clear()

        for (index in tvShowsGenreList.indices) {
            val filmGenre = FilmGenre()
            // filmGenre.genre = tvShowsGenreList[index]
            filmGenre.filmId = moviesId[index]
            listGenre.add(filmGenre)
        }
        return listGenre
    }

}