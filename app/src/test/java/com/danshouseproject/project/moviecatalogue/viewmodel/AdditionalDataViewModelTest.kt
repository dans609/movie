package com.danshouseproject.project.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.danshouseproject.project.moviecatalogue.`object`.Movies
import com.danshouseproject.project.moviecatalogue.`object`.TvShows
import com.danshouseproject.project.moviecatalogue.data.MovieCatalogueRepository
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
class AdditionalDataViewModelTest {

    private lateinit var viewModel: AdditionalDataViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Pair<String, String>>

    @Before
    fun setUp() {
        viewModel = AdditionalDataViewModel(movieCatalogueRepository)
    }

    companion object {
        private val dummyASIBMovies = Movies.generateMovies()[0]
        private val dummyFTTV = TvShows.generateTvShows()[3]
        private const val VALUE_US_ISO_FROM_R = 2131755128
        private const val US_ISO_STRING = "US"
        private const val VALUE_R_CERTIFICATE_FROM_R = 2131755188
        private const val R_CERTIFICATE_STRING = "R"

        private const val VALUE_JP_ISO_FROM_R = 2131755124
        private const val JP_ISO_STRING = "JP"
        private const val VALUE_TV14_CERTIFICATE_FROM_R = 2131755245
        private const val TV14_CERTIFICATE_STRING = "TV-14"
    }

    private fun castStr(data: String?): String =
        data.toString()

    private fun convertRIdToStringIso(iso: Int): String? =
        when (iso) {
            VALUE_US_ISO_FROM_R -> US_ISO_STRING
            VALUE_JP_ISO_FROM_R -> JP_ISO_STRING
            else -> null
        }

    private fun convertRIdToStringCertificate(certificate: Int): String? =
        when (certificate) {
            VALUE_TV14_CERTIFICATE_FROM_R -> TV14_CERTIFICATE_STRING
            VALUE_R_CERTIFICATE_FROM_R -> R_CERTIFICATE_STRING
            else -> null
        }

    @Test
    fun getMoviesAdditionalData() {
        val aStarIsBornAdditionalData = MutableLiveData<Pair<String, String>>()
        val dummyIsoCode = dummyASIBMovies.filmCountryCode
        val dummyCertificate = dummyASIBMovies.filmRatingSymbol
        val movieId = GenreViewModelTest.A_STAR_IS_BORN_ID

        aStarIsBornAdditionalData.value = Pair(castStr(dummyIsoCode), castStr(dummyCertificate))

        `when`(movieCatalogueRepository.getMoviesMoreInfo(movieId)).thenReturn(aStarIsBornAdditionalData)
        val getAdditionalData = viewModel.getMoviesAdditionalData(movieId).value
        verify(movieCatalogueRepository).getMoviesMoreInfo(movieId)

        assertNotNull(getAdditionalData)
        assertEquals(US_ISO_STRING, convertRIdToStringIso(getAdditionalData?.first?.toInt() as Int))
        assertEquals(R_CERTIFICATE_STRING, convertRIdToStringCertificate(getAdditionalData.second.toInt()))

        viewModel.getMoviesAdditionalData(movieId).observeForever(observer)
        verify(observer).onChanged(Pair(castStr(dummyIsoCode), castStr(dummyCertificate)))
    }

    @Test
    fun getTvAdditionalData() {
        val fairyTailAdditionData = MutableLiveData<Pair<String, String>>()
        val dummyIsoCode = dummyFTTV.filmCountryCode
        val dummyCertificate = dummyFTTV.filmRatingSymbol
        println("$dummyIsoCode $dummyCertificate")
        val tvId = GenreViewModelTest.FAIRY_TAIL_TV_ID

        fairyTailAdditionData.value = Pair(castStr(dummyIsoCode), castStr(dummyCertificate))

        `when`(movieCatalogueRepository.getTvMoreInfo(tvId)).thenReturn(fairyTailAdditionData)
        val getAdditionalData = viewModel.getTvAdditionalData(tvId).value
        verify(movieCatalogueRepository).getTvMoreInfo(tvId)

        assertNotNull(getAdditionalData)
        assertEquals(JP_ISO_STRING, convertRIdToStringIso(getAdditionalData?.first?.toInt() as Int))
        assertEquals(TV14_CERTIFICATE_STRING, convertRIdToStringCertificate(getAdditionalData.second.toInt()))

        viewModel.getTvAdditionalData(tvId).observeForever(observer)
        verify(observer).onChanged(Pair(castStr(dummyIsoCode), castStr(dummyCertificate)))
    }
}