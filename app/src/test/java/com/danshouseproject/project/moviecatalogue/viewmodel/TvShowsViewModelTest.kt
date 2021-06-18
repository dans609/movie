package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.`object`.TvShows
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<ListFilm>

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(movieCatalogueRepository)
    }

    companion object {
        private const val ARROW_ID = 1412
        private const val DOOM_PATROL_ID = 79501
        private const val DRAGON_BALL_ID = 12609
        private const val FAIRY_TAIL_ID = 46261
        private const val FAMILY_GUY_ID = 1434
        private const val THE_FLASH_ID = 236
        private const val GAME_OT_ID = 1399
        private const val GOTHAM_ID = 60708
        private const val GREYS_ATM_ID = 1416
        private const val HANNA_ID = 54155
        private const val MARVEL_ID = 62127
        private const val NARUTO_SPD_ID = 31910
        private const val NCIS_ID = 4614
        private const val RIVER_DALE_ID = 69050
        private const val SHAMELESS_ID = 1906
        private const val SUPER_GIRL_ID = 62688
        private const val SUPERNATURAL_ID = 1622
        private const val THE_SIMPSONS_ID = 456
        private const val THE_UMBRELLA_ID = 75006
    }

    @Test
    fun getTvShows() {
        val dummyTv = TvShows.generateTvShows()
        val listTvs = ArrayList<ListFilm>()

        val listTvId = listOf(
            ARROW_ID, DOOM_PATROL_ID, DRAGON_BALL_ID, FAIRY_TAIL_ID, FAMILY_GUY_ID,
            THE_FLASH_ID, GAME_OT_ID, GOTHAM_ID, GREYS_ATM_ID, HANNA_ID,
            MARVEL_ID, NARUTO_SPD_ID, NCIS_ID, RIVER_DALE_ID, SHAMELESS_ID,
            SUPER_GIRL_ID, SUPERNATURAL_ID, THE_SIMPSONS_ID, THE_UMBRELLA_ID
        )

        for (index in listTvId.indices) {
            val tvs = MutableLiveData<ListFilm>()
            tvs.value = dummyTv[index]
            listTvs.add(dummyTv[index])

            `when`(movieCatalogueRepository.getAllTvShows(listTvId[index])).thenReturn(tvs)
            val getTvShows = viewModel.getTvShows(listTvId[index]).value
            verify(movieCatalogueRepository).getAllTvShows(listTvId[index])

            assertNotNull(getTvShows)
            assertEquals(getTvShows?.filmScore, dummyTv[index].filmScore)

            viewModel.getTvShows(listTvId[index]).observeForever(observer)
            verify(observer).onChanged(dummyTv[index])
        }

        assertEquals(MoviesViewModelTest.expextedFilmSize, listTvs.size)
    }
}