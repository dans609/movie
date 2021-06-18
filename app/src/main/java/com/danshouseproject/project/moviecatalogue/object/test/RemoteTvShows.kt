package com.danshouseproject.project.moviecatalogue.`object`.test

import com.danshouseproject.project.moviecatalogue.`object`.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.data.remote.response.FetchFilmGenres
import com.danshouseproject.project.moviecatalogue.data.remote.response.ResponseTvShows

object RemoteTvShows {

    private fun convListGenres(vararg genres: String): List<FetchFilmGenres> {
        val listGenre = ArrayList<FetchFilmGenres>()
        listGenre.clear()

        for (genre in genres)
            listGenre.add(FetchFilmGenres(genre))

        return listGenre
    }

    private fun getConstTvGen(): List<String> {
        val listGenre = ArrayList<String>()
        for (genre in Constant.fetchTvGenre())
            listGenre.add(genre)

        return listGenre
    }

    private val genre = getConstTvGen()

    private val responseTvShowsImage: List<String> = listOf(
        "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
        "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
        "/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
        "/jsYTctFnK8ewomnUgcwhmsTkOum.jpg",
        "/eWWCRjBfLyePh2tfZdvNcIvKSJe.jpg",
        "/fi1GEdCbyWRDHpyJcB25YYK7fh4.jpg",
        "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
        "/4XddcRDtnNjYmLRMYpbrhFxsbuq.jpg",
        "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
        "/iYUtjx1EN4SVTgxd2TB4cZTGSQb.jpg",
        "/4l6KD9HhtD6nCDEfg10Lp6C6zah.jpg",
        "/zAYRe2bJxpWTVrwwmBc00VFkAf4.jpg",
        "/fi8EvaWtL5CvoielOjjVvTr7ux3.jpg",
        "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
        "/kE30UWJvJZ03KIIJMmL6m6YoG0l.jpg",
        "/zsaiq8ZclPuneuN7loLEbsh1ANJ.jpg",
        "/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg",
        "/zLudbPueg8CxGhMS2tyDh3p0TdK.jpg",
        "/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg"
    )

    val responseTvShowsId: List<Int> = listOf(
        1412, 79501, 12609, 46261,
        1434, 236, 1399, 60708,
        1416, 54155, 62127, 31910,
        4614, 69050, 1906, 62688,
        1622, 456, 75006
    )

    private val responseTvShowsScore: List<Double> = listOf(
        6.6, 7.6, 8.1, 7.8,
        7.0, 7.4, 8.4, 7.5,
        8.2, 7.5, 6.6, 8.6,
        7.5, 8.6, 7.6, 7.3,
        8.2, 7.9, 8.7
    )

    private val responseTvShowsTitle: List<String> = listOf(
        "Arrow",
        "Doom Patrol",
        "Dragon Ball",
        "Fairy Tail",
        "Family Guy",
        "The Flash",
        "Game of Thrones",
        "Gotham",
        "Grey's Anatomy",
        "Hanna",
        "Marvel's Iron Fist",
        "Naruto Shippūden",
        "NCIS",
        "Riverdale",
        "Shameless",
        "Supergirl",
        "Supernatural",
        "The Simpsons",
        "The Umbrella Academy"
    )


    private val responseTvShowsGenre: List<List<FetchFilmGenres>> =
        listOf(
            convListGenres(genre[0], genre[1], genre[4]),
            convListGenres(genre[6], genre[2], genre[1]),
            convListGenres(genre[4], genre[7], genre[6]),
            convListGenres(genre[7], genre[3], genre[2], genre[6], genre[4]),
            convListGenres(genre[3], genre[2]),
            convListGenres(genre[7], genre[0], genre[1], genre[6]),
            convListGenres(genre[6], genre[1], genre[7]),
            convListGenres(genre[1], genre[0], genre[6]),
            convListGenres(genre[1]),
            convListGenres(genre[7], genre[1]),
            convListGenres(genre[7], genre[1], genre[6]),
            convListGenres(genre[3], genre[7], genre[6]),
            convListGenres(genre[0], genre[7], genre[1]),
            convListGenres(genre[4], genre[1], genre[0]),
            convListGenres(genre[2], genre[1]),
            convListGenres(genre[1], genre[6], genre[7]),
            convListGenres(genre[1], genre[4], genre[6]),
            convListGenres(genre[5], genre[3], genre[2], genre[1], genre[8]),
            convListGenres(genre[7], genre[6], genre[1])
        )

    fun generateTvShowsResponse(): List<ResponseTvShows> {
        val listTv = ArrayList<ResponseTvShows>()

        for (index in responseTvShowsTitle.indices) {
            val instanciateResponse = ResponseTvShows()
            instanciateResponse.tvTitle = responseTvShowsTitle[index]
            instanciateResponse.tvId = responseTvShowsId[index]
            instanciateResponse.tvPosterPath = responseTvShowsImage[index]
            instanciateResponse.tvGenres = responseTvShowsGenre[index]
            instanciateResponse.tvScore = responseTvShowsScore[index]

            listTv.add(instanciateResponse)
        }
        return listTv
    }

}