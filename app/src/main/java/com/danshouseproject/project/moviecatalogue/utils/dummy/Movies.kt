package com.danshouseproject.project.moviecatalogue.utils.dummy

import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.utils.helper.ConvertTypeHelper.convertListIntToListString
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm

object Movies {

    private val moviesId: List<Int>
        get() = RemoteMovies.responseMoviesId

    private val moviesImage: List<String> = convertListIntToListString(
        R.drawable.poster_a_start_is_born, R.drawable.poster_alita, R.drawable.poster_aquaman,
        R.drawable.poster_bohemian, R.drawable.poster_cold_persuit, R.drawable.poster_creed,
        R.drawable.poster_crimes, R.drawable.poster_glass, R.drawable.poster_how_to_train,
        R.drawable.poster_infinity_war, R.drawable.poster_marry_queen, R.drawable.poster_master_z,
        R.drawable.poster_mortal_engines, R.drawable.poster_overlord, R.drawable.poster_ralph,
        R.drawable.poster_robin_hood, R.drawable.poster_serenity, R.drawable.poster_spiderman,
        R.drawable.poster_t34
    )


    private val moviesName: List<String> = convertListIntToListString(
        R.string.movies_name1,
        R.string.movies_name2,
        R.string.movies_name3,
        R.string.movies_name4,
        R.string.movies_name5,
        R.string.movies_name6,
        R.string.movies_name7,
        R.string.movies_name8,
        R.string.movies_name9,
        R.string.movies_name10,
        R.string.movies_name11,
        R.string.movies_name12,
        R.string.movies_name13,
        R.string.movies_name14,
        R.string.movies_name15,
        R.string.movies_name16,
        R.string.movies_name17,
        R.string.movies_name18,
        R.string.movies_name19
    )

    val moviesRating: List<String> = convertListIntToListString(
        R.string.mpaa_restricted,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_restricted,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_restricted,
        R.string.mpaa_parental_guidance_suggested,
        R.string.mpaa_parents_strongly_cautioned_under_13,
        R.string.mpaa_restricted,
        R.string.mpaa_parental_guidance_suggested,
        R.string.film_rate_12_only_age_or_older
    )

    private val moviesDuration: List<String> = convertListIntToListString(
        R.string.two_hours_sixteen_minutes,
        R.string.two_hours_two_minutes,
        R.string.two_hours_twentythree_minutes,
        R.string.two_hours_fiveteen_minutes,
        R.string.one_hours_fivtynine_minutes,
        R.string.two_hours_teen_minutes,
        R.string.two_hours_fourteen_minutes,
        R.string.two_hours_nine_minutes,
        R.string.one_hours_fortyfour_minutes,
        R.string.two_hours_twentynine_minutes,
        R.string.two_hours_four_minutes,
        R.string.one_hours_fortyseven_minutes,
        R.string.two_hours_nine_minutes,
        R.string.one_hours_fivty_minutes,
        R.string.one_hours_fivtytwo_minutes,
        R.string.one_hours_fivtysix_minutes,
        R.string.one_hours_fortytwo_minutes,
        R.string.one_hours_fivtyseven_minutes,
        R.string.two_hours_nineteen_minutes
    )

    val moviesCountryCode: List<String> = convertListIntToListString(
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_cn
    )

    private val moviesReleaseDate: List<String> = convertListIntToListString(
        R.string.date_format_10052018, R.string.date_format_02142019, R.string.date_format_12212018,
        R.string.date_format_11022018, R.string.date_format_02082019, R.string.date_format_11212018,
        R.string.date_format_11162018, R.string.date_format_01182019, R.string.date_format_01092019,
        R.string.date_format_04272018, R.string.date_format_12212018, R.string.date_format_12262018,
        R.string.date_format_12142018, R.string.date_format_11092018, R.string.date_format_11212018,
        R.string.date_format_11212018, R.string.date_format_01252019, R.string.date_format_12142018,
        R.string.date_format_01012019
    )

    private val moviesDescription: List<String> = convertListIntToListString(
        R.string.movies1_overview, R.string.movies2_overview, R.string.movies3_overview,
        R.string.movies4_overview, R.string.movies5_overview, R.string.movies6_overview,
        R.string.movies7_overview, R.string.movies8_overview, R.string.movies9_overview,
        R.string.movies10_overview, R.string.movies11_overview, R.string.movies12_overview,
        R.string.movies13_overview, R.string.movies14_overview, R.string.movies15_overview,
        R.string.movies16_overview, R.string.movies17_overview, R.string.movies18_overview,
        R.string.movies19_overview
    )

    private val moviesScore: List<Int> = listOf(
        R.integer.score_value75, R.integer.score_value72, R.integer.score_value69,
        R.integer.score_value80, R.integer.score_value57, R.integer.score_value69,
        R.integer.score_value69, R.integer.score_value67, R.integer.score_value78,
        R.integer.score_value83, R.integer.score_value66, R.integer.score_value60,
        R.integer.score_value61, R.integer.score_value67, R.integer.score_value72,
        R.integer.score_value59, R.integer.score_value54, R.integer.score_value84,
        R.integer.score_value69
    )

    fun generateMovies(): List<ListFilm> {
        val list = ArrayList<ListFilm>()

        for (index in moviesName.indices) {
            val movies = ListFilm()
            with(movies) {
                filmId = moviesId[index]
                filmName = moviesName[index]
                filmDuration = moviesDuration[index]
                filmReleaseDate = moviesReleaseDate[index]
                filmOverview = moviesDescription[index]
                filmImage = moviesImage[index]
                filmScore = moviesScore[index]
                isMovies = true
                list.add(movies)
            }
        }
        return list
    }
}