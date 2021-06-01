package com.danshouseproject.project.moviecatalogue.`object`

import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.helper.ConvertTypeHelper.convertListIntToListString
import com.danshouseproject.project.moviecatalogue.model.ListFilm

object TvShows {

    private val tvShowsImage: List<String> = convertListIntToListString(
        R.drawable.poster_arrow, R.drawable.poster_doom_patrol, R.drawable.poster_dragon_ball,
        R.drawable.poster_fairytail, R.drawable.poster_family_guy, R.drawable.poster_flash,
        R.drawable.poster_god, R.drawable.poster_gotham, R.drawable.poster_grey_anatomy,
        R.drawable.poster_hanna, R.drawable.poster_iron_fist, R.drawable.poster_naruto_shipudden,
        R.drawable.poster_ncis, R.drawable.poster_riverdale, R.drawable.poster_shameless,
        R.drawable.poster_supergirl, R.drawable.poster_supernatural, R.drawable.poster_the_simpson,
        R.drawable.poster_the_umbrella
    )

    private val tvShowsName: List<String> = convertListIntToListString(
        R.string.tv_shows_name1, R.string.tv_shows_name2, R.string.tv_shows_name3,
        R.string.tv_shows_name4, R.string.tv_shows_name5, R.string.tv_shows_name6,
        R.string.tv_shows_name7, R.string.tv_shows_name8, R.string.tv_shows_name9,
        R.string.tv_shows_name10, R.string.tv_shows_name11, R.string.tv_shows_name12,
        R.string.tv_shows_name13, R.string.tv_shows_name14, R.string.tv_shows_name15,
        R.string.tv_shows_name16, R.string.tv_shows_name17, R.string.tv_shows_name18,
        R.string.tv_shows_name19
    )

    private val tvShowsRating: List<String> = convertListIntToListString(
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_adult_certificate,
        R.string.tv_rate_canada_kids_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_kids_certificate,
        R.string.tv_rate_canada_adult_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_adult_certificate,
        R.string.tv_rate_canada_adult_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_18_old_and_over,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_teens_certificate,
        R.string.tv_rate_canada_kids_certificate,
        R.string.tv_rate_canada_adult_certificate
    )

    private val tvShowsDuration: List<String> = convertListIntToListString(
        R.string.fortytwo_minutes, R.string.fortynine_minutes, R.string.twentyfive_minutes,
        R.string.twentyfive_minutes, R.string.twentytwo_minutes, R.string.fortyfive_minutes,
        R.string.one_hours, R.string.fortythree_minutes, R.string.fortythree_minutes,
        R.string.fivty_minutes, R.string.fivtyfive_minutes, R.string.twentyfive_minutes,
        R.string.fortyfive_minutes, R.string.fortyfive_minutes, R.string.one_hours,
        R.string.fortytwo_minutes, R.string.fortyfive_minutes, R.string.twentytwo_minutes,
        R.string.fivtyfive_minutes
    )

    private val tvShowsCountryCode: List<String> = convertListIntToListString(
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_jp,
        R.string.iso_alpha2_jp, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_jp,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_uk,
        R.string.iso_alpha2_us, R.string.iso_alpha2_us, R.string.iso_alpha2_us,
        R.string.iso_alpha2_us
    )

    private val tvShowsReleaseDate: List<String> = convertListIntToListString(
        R.string.date_format_yyyy_2012,
        R.string.date_format_yyyy_2019,
        R.string.date_format_yyyy_1986,
        R.string.date_format_yyyy_2009,
        R.string.date_format_yyyy_1999,
        R.string.date_format_yyyy_1990,
        R.string.date_format_yyyy_2011,
        R.string.date_format_yyyy_2014,
        R.string.date_format_yyyy_2005,
        R.string.date_format_yyyy_2019,
        R.string.date_format_yyyy_2017,
        R.string.date_format_yyyy_2007,
        R.string.date_format_yyyy_2003,
        R.string.date_format_yyyy_2017,
        R.string.date_format_yyyy_2004,
        R.string.date_format_yyyy_2015,
        R.string.date_format_yyyy_2005,
        R.string.date_format_yyyy_1989,
        R.string.date_format_yyyy_2019
    )

    private val tvShowsDescription: List<String> = convertListIntToListString(
        R.string.tv_shows1_overview, R.string.tv_shows2_overview, R.string.tv_shows3_overview,
        R.string.tv_shows4_overview, R.string.tv_shows5_overview, R.string.tv_shows6_overview,
        R.string.tv_shows7_overview, R.string.tv_shows8_overview, R.string.tv_shows9_overview,
        R.string.tv_shows10_overview, R.string.tv_shows11_overview, R.string.tv_shows12_overview,
        R.string.tv_shows13_overview, R.string.tv_shows14_overview, R.string.tv_shows15_overview,
        R.string.tv_shows16_overview, R.string.tv_shows17_overview, R.string.tv_shows18_overview,
        R.string.tv_shows19_overview
    )

    private val tvShowsScore: List<Int> = listOf(
        R.integer.score_value66, R.integer.score_value76, R.integer.score_value81,
        R.integer.score_value76, R.integer.score_value70, R.integer.score_value74,
        R.integer.score_value84, R.integer.score_value75, R.integer.score_value82,
        R.integer.score_value75, R.integer.score_value66, R.integer.score_value86,
        R.integer.score_value74, R.integer.score_value86, R.integer.score_value76,
        R.integer.score_value73, R.integer.score_value82, R.integer.score_value78,
        R.integer.score_value87
    )

    fun generateTvShows(): List<ListFilm> {
        val list = ArrayList<ListFilm>()

        for (index in tvShowsName.indices) {
            val tvShows = ListFilm()
            with(tvShows) {
                filmName = tvShowsName[index]
                filmRatingSymbol = tvShowsRating[index]
                filmDuration = tvShowsDuration[index]
                filmReleaseDate = tvShowsReleaseDate[index]
                filmCountryCode = tvShowsCountryCode[index]
                filmOverview = tvShowsDescription[index]
                filmImage = tvShowsImage[index]
                filmScore = tvShowsScore[index]
                list.add(tvShows)
            }
        }
        return list
    }
}