package com.danshouseproject.project.moviecatalogue.`object`.test.constant

object Constant {
    private const val GENRE_DRAMA = "Drama"
    private const val GENRE_ACTION = "Action"
    private const val GENRE_ANIMATION = "Animation"
    private const val GENRE_SCIFI = "Science Fiction"
    private const val GENRE_ADVENTURE = "Adventure"
    private const val GENRE_COMEDY = "Comedy"
    private const val GENRE_ROMANCE = "Romance"
    private const val GENRE_MUSIC = "Music"
    private const val GENRE_WAR = "War"
    private const val GENRE_HISTORY = "History"
    private const val GENRE_THRILLER = "Thriller"
    private const val GENRE_MYSTERY = "Mystery"
    private const val GENRE_FAMILY = "Family"
    private const val GENRE_HORROR = "Horror"
    private const val GENRE_FANTASY = "Fantasy"
    private const val GENRE_CRIME = "Crime"
    private const val GENRE_SCIFI_FANTASY = "Sci-Fi & Fantasy"
    private const val GENRE_ACTION_ADVENTURE = "Action & Adventure"
    private const val GENRE_REALITY = "Reality"

    const val US_ISO_CODE = "US"
    const val GB_ISO_CODE = "GB"
    const val DE_ISO_CODE = "DE"
    const val RU_ISO_CODE = "RU"

    const val R_MOVIE_CERTIFICATE = "R"
    const val PG13_MOVIE_CERTIFICATE = "PG-13"
    const val PG_MOVIE_CERTIFICATE = "PG"
    const val MORE_THAN_TWELVE = "12+"

    const val TVMA_TV_CERTIFICATE = "TV-MA"
    const val TVPG_TV_CERTIFICATE = "TV-PG"
    const val TV14_TV_CERTIFICATE = "TV-14"
    const val EQUALS_OR_OLDER_T12 = "12"
    const val EQUALS_OT_OLDER_T15 = "15"
    const val EQUALS_OR_OLDER_T16 = "16"
    const val EQUALS_OR_OLDER_T18 = "18"

    fun fetchMoviesGenre(): List<String> =
        listOf(
            GENRE_ACTION, GENRE_HISTORY, GENRE_DRAMA, GENRE_WAR,
            GENRE_COMEDY, GENRE_SCIFI, GENRE_ANIMATION, GENRE_ADVENTURE,
            GENRE_MYSTERY, GENRE_THRILLER, GENRE_FAMILY, GENRE_HORROR,
            GENRE_FANTASY, GENRE_CRIME, GENRE_MUSIC, GENRE_ROMANCE
        )

    fun fetchTvGenre(): List<String> =
        listOf(
            GENRE_CRIME, GENRE_DRAMA, GENRE_COMEDY, GENRE_ANIMATION,
            GENRE_MYSTERY, GENRE_FAMILY, GENRE_SCIFI_FANTASY, GENRE_ACTION_ADVENTURE,
            GENRE_REALITY
        )

}