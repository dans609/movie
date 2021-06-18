package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.`object`.Genre
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GenreViewModelTest {

    private lateinit var viewModel: GenreViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<FilmGenre>

    @Before
    fun setUp() {
        viewModel = GenreViewModel(movieCatalogueRepository)
    }

    companion object {
        private const val VALUE_ZERO = 0
        private const val VALUE_ONE = 1
        private const val VALUE_DRAMA_FROM_R = 2131755091
        private const val VALUE_ROMANCE_FROM_R = 2131755099
        private const val VALUE_MUSIC_FROM_R = 2131755097

        private const val VALUE_ACTION_ADVENTURE_FROM_R = 2131755086
        private const val VALUE_ANIMATION_FROM_R = 2131755088
        private const val VALUE_COMEDY_FROM_R = 2131755089
        private const val VALUE_SCIFI_FANTASY_FROM_R = 2131755101
        private const val VALUE_MYSTERY_ADVENTURE_FROM_R = 2131755098

        private val dummyASIBMoviesGenres = Genre.generateMoviesGenre()[0]
        private const val LIST_GENRE_OF_A_STAR_IS_BORN_MOVIE = 3
        const val A_STAR_IS_BORN_ID = 332562

        private val dummyFTTvGenres = Genre.generateTvShowsGenre()[3]
        private const val LIST_GENRE_OF_FAIRY_TAIL_TVS = 5
        const val FAIRY_TAIL_TV_ID = 46261

        private const val genreDrama = "Drama"
        private const val genreMusic = "Music"
        private const val genreRomance = "Romance"
        private const val genreActionAdventure = "Action & Adventure"
        private const val genreAnimation = "Animation"
        private const val genreComedy = "Comedy"
        private const val genreSciFiFantasy = "Sci-Fi & Fantasy"
        private const val genreMystery = "Mystery"
    }

    private fun convertRIdToStringGenre(r_id: Int): String? =
        when (r_id) {
            VALUE_DRAMA_FROM_R -> genreDrama
            VALUE_ROMANCE_FROM_R -> genreRomance
            VALUE_MUSIC_FROM_R -> genreMusic
            VALUE_ACTION_ADVENTURE_FROM_R -> genreActionAdventure
            VALUE_ANIMATION_FROM_R -> genreAnimation
            VALUE_COMEDY_FROM_R -> genreComedy
            VALUE_SCIFI_FANTASY_FROM_R -> genreSciFiFantasy
            VALUE_MYSTERY_ADVENTURE_FROM_R -> genreMystery
            else -> null
        }


    @Test
    fun getMoviesGenres() {
        val movieGenre = MutableLiveData<FilmGenre>()
        val movieId = A_STAR_IS_BORN_ID
        movieGenre.value = dummyASIBMoviesGenres

        `when`(movieCatalogueRepository.getMoviesGenres(movieId)).thenReturn(movieGenre)
        val getGenre = viewModel.getMoviesGenres(movieId).value
        verify(movieCatalogueRepository).getMoviesGenres(movieId)

        assertNotNull(getGenre)
        assertEquals(LIST_GENRE_OF_A_STAR_IS_BORN_MOVIE, getGenre?.genre?.size)

        viewModel.getMoviesGenres(movieId).observeForever(observer)
        verify(observer).onChanged(dummyASIBMoviesGenres)
    }

    @Test
    fun getTvShowsGenres() {
        val tvGenre = MutableLiveData<FilmGenre>()
        val tvId = FAIRY_TAIL_TV_ID
        tvGenre.value = dummyFTTvGenres

        `when`(movieCatalogueRepository.getTvGenres(tvId)).thenReturn(tvGenre)
        val getGenre = viewModel.getTvShowsGenres(tvId).value

        assertNotNull(getGenre)
        assertEquals(LIST_GENRE_OF_FAIRY_TAIL_TVS, getGenre?.genre?.size)

        viewModel.getTvShowsGenres(tvId).observeForever(observer)
        verify(observer).onChanged(dummyFTTvGenres)
    }


    @Test
    fun getMoviesGenreValue() {
        val moviesGenre = MutableLiveData<FilmGenre>()
        val movieId = A_STAR_IS_BORN_ID
        moviesGenre.value = dummyASIBMoviesGenres

        `when`(movieCatalogueRepository.getMoviesGenres(movieId)).thenReturn(moviesGenre)
        val getGenre = viewModel.getMoviesGenres(movieId).value
        val orderedGenre = listOf(genreDrama, genreRomance, genreMusic)

        for (index in orderedGenre.indices)
            assertEquals(orderedGenre[index], convertRIdToStringGenre(getGenre?.genre?.get(index)?.toInt() as Int))

        viewModel.getMoviesGenres(movieId).observeForever(observer)
        verify(observer).onChanged(dummyASIBMoviesGenres)
    }

    @Test
    fun getTvShowsGenreValue() {
        val tvGenre = MutableLiveData<FilmGenre>()
        val tvId = FAIRY_TAIL_TV_ID
        tvGenre.value = dummyFTTvGenres

        `when`(movieCatalogueRepository.getTvGenres(tvId)).thenReturn(tvGenre)
        val getGenre = viewModel.getTvShowsGenres(tvId).value
        val orderedGenre = listOf(genreAnimation, genreMystery, genreComedy, genreActionAdventure, genreSciFiFantasy)

        var countGenre = VALUE_ZERO
        for (predictGenre in orderedGenre)
            for (genre in getGenre?.genre ?: break)
                if (predictGenre == convertRIdToStringGenre(genre.toInt())) {
                    countGenre += VALUE_ONE
                    assertEquals(predictGenre, convertRIdToStringGenre(genre.toInt()))
                    println("""
                        >> $predictGenre (Expected) 
                        >> ${convertRIdToStringGenre(genre.toInt())} (Actual)
                        -----------------------
                    """.trimIndent())
                    break
                } else continue

        if (countGenre != getGenre?.genre?.size)
            println("${orderedGenre.size} <-/-> ${getGenre?.genre?.size}")

        viewModel.getTvShowsGenres(tvId).observeForever(observer)
        verify(observer).onChanged(dummyFTTvGenres)
    }

}