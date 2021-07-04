package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.utils.dummy.Genre
import com.danshouseproject.project.moviecatalogue.core.components.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource
import com.danshouseproject.project.moviecatalogue.view.detail.GenreViewModel
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
    private lateinit var observer: Observer<Resource<FilmGenre>>

    @Before
    fun setUp() {
        viewModel = GenreViewModel(movieCatalogueRepository)
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
            else -> {
                printIfResourceIdIsNull()
                null
            }
        }


    @Test
    fun getMoviesGenres() {
        val expectedASIBGenreSize = 3
        val movieId = A_STAR_IS_BORN_ID
        val dummyGenre = Genre.generateMoviesGenre()
        val movieGenre = MutableLiveData<Resource<FilmGenre>>()

        for (idxGenre in dummyGenre.indices) {
            if (dummyGenre[idxGenre].filmId == movieId) {
                movieGenre.value = Resource.success(dummyGenre[idxGenre])
                break
            } else continue
        }

        `when`(movieCatalogueRepository.getMoviesGenres(movieId)).thenReturn(movieGenre)
        val genreEntity = viewModel.getMoviesGenres(movieId).value?.data as FilmGenre
        verify(movieCatalogueRepository).getMoviesGenres(movieId)

        assertNotNull(genreEntity)
        assertNotNull(genreEntity.genre)

        val listGenre = ArrayList<String>()
        for (idxGenre in genreEntity.genre!!.indices) {
            if (genreEntity.genre!![idxGenre].filmId == movieId)
                listGenre.add(genreEntity.genre!![idxGenre].genre)
            else continue
        }

        assertEquals(expectedASIBGenreSize, listGenre.size)
        viewModel.getMoviesGenres(movieId).observeForever(observer)
        verify(observer).onChanged(movieGenre.value)
    }


    @Test
    fun getTvShowsGenres() {
        val expectedFTTVGenreSize = 5
        val tvId = FAIRY_TAIL_TV_ID
        val dummyGenre = Genre.generateTvShowsGenre()
        val tvGenre = MutableLiveData<Resource<FilmGenre>>()

        for (idxGenre in dummyGenre.indices) {
            if (dummyGenre[idxGenre].filmId == tvId) {
                tvGenre.value = Resource.success(dummyGenre[idxGenre])
                break
            } else continue
        }

        `when`(movieCatalogueRepository.getTvGenres(tvId)).thenReturn(tvGenre)
        val genreEntity = viewModel.getTvShowsGenres(tvId).value?.data as FilmGenre
        verify(movieCatalogueRepository).getTvGenres(tvId)

        assertNotNull(genreEntity)
        assertNotNull(genreEntity.genre)

        val listGenre = ArrayList<String>()
        for (idxGenre in genreEntity.genre!!.indices) {
            if (genreEntity.genre!![idxGenre].filmId == tvId)
                listGenre.add(genreEntity.genre!![idxGenre].genre)
            else continue
        }

        assertEquals(expectedFTTVGenreSize, listGenre.size)
        viewModel.getTvShowsGenres(tvId).observeForever(observer)
        verify(observer).onChanged(tvGenre.value)
    }


    @Test
    fun getMoviesGenreValue() {
        val movieId = A_STAR_IS_BORN_ID
        val dummyGenre = Genre.generateMoviesGenre()
        val movieGenre = MutableLiveData<Resource<FilmGenre>>()

        for (idxGenre in dummyGenre.indices) {
            if (dummyGenre[idxGenre].filmId == movieId) {
                movieGenre.value = Resource.success(dummyGenre[idxGenre])
                break
            } else continue
        }

        `when`(movieCatalogueRepository.getMoviesGenres(movieId)).thenReturn(movieGenre)
        val genreEntity = viewModel.getMoviesGenres(movieId).value?.data as FilmGenre
        verify(movieCatalogueRepository).getMoviesGenres(movieId)

        assertNotNull(genreEntity)
        assertNotNull(genreEntity.genre)

        val orderedExpectedGenre = listOf(genreDrama, genreRomance, genreMusic)
        val listGenre = ArrayList<String>()

        for (idxGenre in genreEntity.genre!!.indices) {
            if (genreEntity.genre!![idxGenre].filmId == movieId)
                listGenre.add(genreEntity.genre!![idxGenre].genre)
            else continue
        }

        for (index in orderedExpectedGenre.indices)
            assertEquals(
                orderedExpectedGenre[index],
                convertRIdToStringGenre(listGenre[index].toInt())
            )

        viewModel.getMoviesGenres(movieId).observeForever(observer)
        verify(observer).onChanged(movieGenre.value)
    }

    @Test
    fun getTvShowsGenreValue() {
        val tvId = FAIRY_TAIL_TV_ID
        val dummyGenre = Genre.generateTvShowsGenre()
        val tvGenre = MutableLiveData<Resource<FilmGenre>>()

        for (idxGenre in dummyGenre.indices) {
            if (dummyGenre[idxGenre].filmId == tvId) {
                tvGenre.value = Resource.success(dummyGenre[idxGenre])
                break
            } else continue
        }

        `when`(movieCatalogueRepository.getTvGenres(tvId)).thenReturn(tvGenre)
        val genreEntity = viewModel.getTvShowsGenres(tvId).value?.data as FilmGenre
        verify(movieCatalogueRepository).getTvGenres(tvId)

        assertNotNull(genreEntity)
        assertNotNull(genreEntity.genre)

        val unOrderedExpectedGenre = listOf(
            genreAnimation,
            genreMystery,
            genreComedy,
            genreActionAdventure,
            genreSciFiFantasy
        )
        val listGenre = ArrayList<String>()
        for (idxGenre in genreEntity.genre!!.indices) {
            if (genreEntity.genre!![idxGenre].filmId == tvId)
                listGenre.add(genreEntity.genre!![idxGenre].genre)
            else continue
        }

        for (index in unOrderedExpectedGenre.indices) {
            for (idxGenre in listGenre.indices) {
                val actualGenre = convertRIdToStringGenre(listGenre[index].toInt())
                if (unOrderedExpectedGenre[index] == actualGenre) {
                    assertEquals(unOrderedExpectedGenre[index], actualGenre)
                    break
                } else continue
            }
        }

        viewModel.getTvShowsGenres(tvId).observeForever(observer)
        verify(observer).onChanged(tvGenre.value)
    }


    companion object {
        private const val VALUE_DRAMA_FROM_R = 2131755115
        private const val VALUE_ROMANCE_FROM_R = 2131755123
        private const val VALUE_MUSIC_FROM_R = 2131755121

        private const val VALUE_ACTION_ADVENTURE_FROM_R = 2131755110
        private const val VALUE_ANIMATION_FROM_R = 2131755112
        private const val VALUE_COMEDY_FROM_R = 2131755113
        private const val VALUE_SCIFI_FANTASY_FROM_R = 2131755125
        private const val VALUE_MYSTERY_ADVENTURE_FROM_R = 2131755122

        const val A_STAR_IS_BORN_ID = 332562
        const val FAIRY_TAIL_TV_ID = 46261

        private const val genreDrama = "Drama"
        private const val genreMusic = "Music"
        private const val genreRomance = "Romance"
        private const val genreActionAdventure = "Action & Adventure"
        private const val genreAnimation = "Animation"
        private const val genreComedy = "Comedy"
        private const val genreSciFiFantasy = "Sci-Fi & Fantasy"
        private const val genreMystery = "Mystery"

        private fun printIfResourceIdIsNull() =
            println(
                """
                Error: NullPointerExeption, data is not exists
                cause: the layout id is already changed, maybe because you add a new resource in above of your expected resource id
            """.trimIndent()
            )
    }
}