package com.danshouseproject.project.moviecatalogue.utils.dummy.test

import com.danshouseproject.project.moviecatalogue.utils.dummy.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.FetchFilmGenres
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies

object RemoteMovies {

    private fun convListGenres(vararg genres: String): List<FetchFilmGenres> {
        val listGenre = ArrayList<FetchFilmGenres>()
        listGenre.clear()

        for (genre in genres)
            listGenre.add(FetchFilmGenres(genre))

        return listGenre
    }

    private fun getConstMovGens(): List<String> {
        val listGenre = ArrayList<String>()
        for (genre in Constant.fetchMoviesGenre())
            listGenre.add(genre)

        return listGenre
    }

    private val genre = getConstMovGens()

    private val responseMoviesImage: List<String> =
        listOf(
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/xRWht48C2V8XNfzvPehyClOvDni.jpg",
            "/xLPffWMhMj1l50ND3KchMjYoKmE.jpg",
            "/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
            "/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
            "/v3QyboWRoA4O9RbcsqH8tJMe8EB.jpg",
            "/fMMrl8fD9gRCFJvsx0SuFwkEOop.jpg",
            "/svIDTNUoajS8dLEo7EosxvyAsgJ.jpg",
            "/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg",
            "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "/b5RMzLAyq5QW6GtN9sIeAEMLlBI.jpg",
            "/6VxEvOF7QDovsG6ro9OVyjH07LF.jpg",
            "/gLhYg9NIvIPKVRTtvzCWnp1qJWG.jpg",
            "/l76Rgp32z2UxjULApxGXAPpYdAP.jpg",
            "/qEnH5meR381iMpmCumAIMswcQw2.jpg",
            "/AiRfixFcfTkNbn2A73qVJPlpkUo.jpg",
            "/hgWAcic93phg4DOuQ8NrsgQWiqu.jpg",
            "/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
            "/jqBIHiSglRfNxjh1zodHLa9E7zW.jpg"
        )

    val responseMoviesId: List<Int> =
        listOf(
            332562, 399579, 297802, 424694,
            438650, 480530, 338952, 450465,
            166428, 299536, 457136, 450001,
            428078, 438799, 404368, 375588,
            452832, 324857, 505954
        )

    private val responseMoviesName: List<String> =
        listOf(
            "A Star Is Born",
            "Alita: Battle Angel",
            "Aquaman",
            "Bohemian Rhapsody",
            "Cold Pursuit",
            "Creed II",
            "Fantastic Beasts: The Crimes of Grindelwald",
            "Glass",
            "How to Train Your Dragon: The Hidden World",
            "Avengers: Infinity War",
            "Mary Queen of Scots",
            "葉問外傳：張天志",
            "Mortal Engines",
            "Overlord",
            "Ralph Breaks the Internet",
            "Robin Hood",
            "Serenity",
            "Spider-Man: Into the Spider-Verse",
            "T-34"
        )

    val responseMoviesScore: List<Double> = listOf(
        7.5, 7.2, 6.9, 8.0,
        5.7, 6.9, 6.9, 6.7,
        7.8, 8.3, 6.6, 6.0,
        6.1, 6.7, 7.2, 5.9,
        5.4, 8.4, 6.9
    )


    private val responseMoviesGenres: List<List<FetchFilmGenres>> =
        listOf(
            convListGenres(genre[2], genre[genre.size - 1], genre[genre.size - 2]),
            convListGenres(genre[0], genre[5], genre[7]),
            convListGenres(genre[0], genre[7], genre[genre.size - 4]),
            convListGenres(genre[genre.size - 2], genre[2], genre[1]),
            convListGenres(genre[0], genre[genre.size - 3], genre[9]),
            convListGenres(genre[2]),
            convListGenres(genre[7], genre[genre.size - 4], genre[2]),
            convListGenres(genre[9], genre[2], genre[5]),
            convListGenres(genre[6], genre[10], genre[7]),
            convListGenres(genre[7], genre[0], genre[5]),
            convListGenres(genre[2], genre[1]),
            convListGenres(genre[0]),
            convListGenres(genre[7], genre[5]),
            convListGenres(genre[genre.size - 5], genre[3], genre[5]),
            convListGenres(genre[10], genre[6], genre[4], genre[7]),
            convListGenres(genre[7], genre[0], genre[9]),
            convListGenres(genre[9], genre[8], genre[2]),
            convListGenres(
                genre[0],
                genre[7],
                genre[6],
                genre[5],
                genre[4]
            ),
            convListGenres(genre[3], genre[0], genre[2], genre[1])
        )

    fun generateMoviesResponse(): List<ResponseMovies> {
        val listMovies = ArrayList<ResponseMovies>()

        for (index in responseMoviesName.indices) {
            val instanciateResponse = ResponseMovies()
            instanciateResponse.moviesTitle = responseMoviesName[index]
            instanciateResponse.moviesId = responseMoviesId[index]
            instanciateResponse.moviesPosterPath = responseMoviesImage[index]
            instanciateResponse.moviesGenres = responseMoviesGenres[index]
            instanciateResponse.moviesScore = responseMoviesScore[index]

            listMovies.add(instanciateResponse)
        }
        return listMovies
    }
}