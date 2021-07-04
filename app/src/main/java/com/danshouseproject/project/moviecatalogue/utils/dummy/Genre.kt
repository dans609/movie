package com.danshouseproject.project.moviecatalogue.utils.dummy

import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.utils.helper.ConvertTypeHelper.convertListIntToListString
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.GenreConverter

object Genre {

    private val moviesId: List<Int>
        get() = RemoteMovies.responseMoviesId

    private val tvId: List<Int>
        get() = RemoteTvShows.responseTvShowsId

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

        var counterId = 0
        for (index in moviesGenreList.indices) {
            val listGenreConverter = ArrayList<GenreConverter>()
            for (idxGenre in moviesGenreList[index].indices) {
                listGenreConverter.add(GenreConverter(counterId, moviesId[index], moviesGenreList[index][idxGenre]))
                counterId++
            }

            val filmGenre = FilmGenre(
                id = index,
                filmId = moviesId[index],
                genre = listGenreConverter,
                isMovies = true
            )

            listGenre.add(filmGenre)
        }
        return listGenre
    }


    fun generateTvShowsGenre(): List<FilmGenre> {
        val listGenre = ArrayList<FilmGenre>()
        listGenre.clear()

        var counterId = 0
        for (index in tvShowsGenreList.indices) {
            val listGenreConverter = ArrayList<GenreConverter>()
            for (idxGenre in tvShowsGenreList[index].indices) {
                listGenreConverter.add(GenreConverter(counterId, tvId[index], tvShowsGenreList[index][idxGenre]))
                counterId++
            }

            val filmGenre = FilmGenre(
                id = index,
                filmId = tvId[index],
                genre = listGenreConverter,
            )

            listGenre.add(filmGenre)
        }
        return listGenre
    }
}