package com.danshouseproject.project.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.danshouseproject.project.moviecatalogue.`object`.test.LiveDataTestUtils
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteAdditionalData
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.data.remote.*
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MovieCatalogueRepositoryTest {

    companion object {
        private const val ZERO_VALUE = 0
        private const val ONE_VALUE = 1
        private const val VALUE_FOR_REPLACE_FIRST_31CHAR = 31
        private const val NO_VALUE = ""
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val fakeMovieCatalogueRepository = FakeMovieCatalogueRepository(remote)

    private val moviesResponse = RemoteMovies.generateMoviesResponse()
    private val moviesASIBIdResponse = moviesResponse[0].moviesId

    private val tvShowsResponse = RemoteTvShows.generateTvShowsResponse()
    private val tvShowsFTIdResponse = tvShowsResponse[3].tvId

    private val moviesAdditinoalData =
        RemoteAdditionalData.generateMoviesAdditionalDataResponse(moviesASIBIdResponse)
    private val tvAdditionalData =
        RemoteAdditionalData.generateTvsAdditionalDataResponse(tvShowsFTIdResponse)


    @Test
    fun getAllMovies() {
        val data = ArrayList<ListFilm>()

        for (index in moviesResponse.indices) {
            val movieRsp = moviesResponse[index]
            doAnswer { invocation ->
                (invocation.arguments[ONE_VALUE] as LoadMoviesResponse)
                    .onMoviesLoaded(movieRsp)
                null
            }.`when`(remote).fetchMovies(eq(movieRsp.moviesId), any())

            val moviesEntity =
                LiveDataTestUtils.getValue(fakeMovieCatalogueRepository.getAllMovies(movieRsp.moviesId))
            verify(remote).fetchMovies(eq(movieRsp.moviesId), any())

            val removeDuplicatePath = moviesEntity.filmImage.take(VALUE_FOR_REPLACE_FIRST_31CHAR)
            val moviesPath = moviesEntity.filmImage.replaceFirst(removeDuplicatePath, NO_VALUE)
            data.add(moviesEntity)

            assertNotNull(moviesEntity)
            assertEquals(movieRsp.moviesPosterPath, moviesPath)
            assertEquals(movieRsp.moviesTitle, moviesEntity.filmName)

            printValue(movieRsp.moviesTitle, moviesEntity.filmName)
        }
        assertEquals(moviesResponse.size, data.size)
    }


    @Test
    fun getAllTvShows() {
        val data = ArrayList<ListFilm>()

        for (index in tvShowsResponse.indices) {
            val tvResponse = tvShowsResponse[index]

            doAnswer { invocation ->
                (invocation.arguments[ONE_VALUE] as LoadTvResponse)
                    .onTvShowsLoaded(tvResponse)
                null
            }.`when`(remote).fetchTvShows(eq(tvResponse.tvId), any())

            val tvEntity = LiveDataTestUtils.getValue(
                fakeMovieCatalogueRepository.getAllTvShows(tvResponse.tvId)
            )
            verify(remote).fetchTvShows(eq(tvResponse.tvId), any())

            val removeDuplicatePath = tvEntity.filmImage.take(VALUE_FOR_REPLACE_FIRST_31CHAR)
            val tvPath = tvEntity.filmImage.replaceFirst(removeDuplicatePath, NO_VALUE)
            data.add(tvEntity)

            assertNotNull(tvEntity)
            assertEquals(tvResponse.tvPosterPath, tvPath)
            assertEquals(tvResponse.tvTitle, tvEntity.filmName)

            printValue(tvResponse.tvTitle, tvEntity.filmName)
        }
        assertEquals(data.size, tvShowsResponse.size)
    }


    @Test
    fun getMoviesGenres() {
        val listMoviesGenre = ArrayList<FilmGenre>()

        for (index in moviesResponse.indices) {
            val responseMovies = moviesResponse[index]

            doAnswer { invocation ->
                (invocation.arguments[ONE_VALUE] as LoadMoviesResponse)
                    .onMoviesLoaded(responseMovies)
                null
            }.`when`(remote).fetchMovies(eq(responseMovies.moviesId), any())

            val movieEntity = LiveDataTestUtils.getValue(
                fakeMovieCatalogueRepository.getMoviesGenres(responseMovies.moviesId)
            )
            verify(remote).fetchMovies(eq(responseMovies.moviesId), any())

            assertNotNull(movieEntity)
            assertNotNull(movieEntity.genre)
            assertNotNull(movieEntity.filmId)

            val movieEntityGenre = movieEntity.genre
            val movieEntityId = movieEntity.filmId
            val instanceGenre = FilmGenre(movieEntityGenre, movieEntityId)
            listMoviesGenre.add(instanceGenre)

            val listMovieEntityGenre = ArrayList<String>()

            printHeader(responseMovies.moviesTitle, responseMovies.moviesGenres?.size as Int)
            for (genreIdx in movieEntityGenre?.indices ?: return) {
                printValue(
                    responseMovies.moviesGenres?.get(genreIdx)?.genre,
                    movieEntityGenre[genreIdx]
                )
                listMovieEntityGenre.add(movieEntityGenre[genreIdx])

                assertEquals(
                    responseMovies.moviesGenres?.get(genreIdx)?.genre,
                    movieEntityGenre[genreIdx]
                )
            }

            printDashes()
            assertEquals(responseMovies.moviesGenres?.size, listMovieEntityGenre.size)
        }

        assertEquals(moviesResponse.size, listMoviesGenre.size)
    }


    @Test
    fun getTvGenres() {
        val numbertOfActualFilm = ArrayList<FilmGenre>()

        for (index in tvShowsResponse.indices) {
            val response = tvShowsResponse[index]

            doAnswer { invocation ->
                (invocation.arguments[ONE_VALUE] as LoadTvResponse)
                    .onTvShowsLoaded(response)
                null
            }.`when`(remote).fetchTvShows(eq(response.tvId), any())

            val resultTvEntity =
                LiveDataTestUtils.getValue(fakeMovieCatalogueRepository.getTvGenres(response.tvId))
            verify(remote).fetchTvShows(eq(response.tvId), any())

            resultTvEntity.let { actualEn ->
                assertNotNull(actualEn)
                assertNotNull(actualEn.genre)
                assertNotNull(actualEn.filmId)

                val tvEntityGenre = actualEn.genre
                val tvEntityId = actualEn.filmId
                val instanceGenre = FilmGenre(tvEntityGenre, tvEntityId)
                numbertOfActualFilm.add(instanceGenre)

                val numberOfTvGenres = ArrayList<String>()
                printHeader(response.tvTitle, response.tvGenres?.size as Int)

                for (idxGenre in tvEntityGenre?.indices ?: return)
                    response.tvGenres?.also { tvGenre ->
                        numberOfTvGenres.add(tvEntityGenre[idxGenre])
                        printValue(tvGenre[idxGenre].genre, tvEntityGenre[idxGenre])
                        assertEquals(tvGenre[idxGenre].genre, tvEntityGenre[idxGenre])
                    }

                printDashes()
                assertEquals(response.tvGenres?.size, numberOfTvGenres.size)
            }
        }
        assertEquals(tvShowsResponse.size, numbertOfActualFilm.size)
    }


    @Test
    fun getMoviesMoreInfo() {

        doAnswer { invocation ->
            (invocation.arguments[ONE_VALUE] as LoadMoviesMoreInfo)
                .onMoviesAdditionInformationReceived(moviesAdditinoalData)
            null
        }.`when`(remote).fetchMoviesMoreInfo(eq(moviesASIBIdResponse), any())

        val moviesMoreInfoEntity =
            LiveDataTestUtils.getValue(
                fakeMovieCatalogueRepository.getMoviesMoreInfo(
                    moviesASIBIdResponse
                )
            )
        verify(remote).fetchMoviesMoreInfo(eq(moviesASIBIdResponse), any())

        moviesAdditinoalData.result?.get(ZERO_VALUE)?.let { expectRes ->
            assertNotNull(moviesMoreInfoEntity)
            assertEquals(expectRes.isoCode, moviesMoreInfoEntity.first)
            assertEquals(
                expectRes.moviesCertificate?.get(ZERO_VALUE)?.certificate,
                moviesMoreInfoEntity.second
            )

            printValue(expectRes.isoCode, moviesMoreInfoEntity.first)
            printValue(
                expectRes.moviesCertificate?.get(ZERO_VALUE)?.certificate,
                moviesMoreInfoEntity.second
            )
        }
        printDashes()
    }

    @Test
    fun getTvMoreInfo() {
        doAnswer { invocation ->
            (invocation.arguments[ONE_VALUE] as LoadTvMoreInfo)
                .onTvShowsAdditionInformatonReceived(tvAdditionalData)
            null
        }.`when`(remote).fetchTvMoreInfo(eq(tvShowsFTIdResponse), any())

        val tvMoreInfoEntity =
            LiveDataTestUtils.getValue(
                fakeMovieCatalogueRepository.getTvMoreInfo(
                    tvShowsFTIdResponse
                )
            )
        verify(remote).fetchTvMoreInfo(eq(tvShowsFTIdResponse), any())

        tvAdditionalData.result?.get(ZERO_VALUE)?.let { expectRes ->
            tvMoreInfoEntity.also { actualRes ->
                assertNotNull(tvMoreInfoEntity)
                assertEquals(expectRes.isoCode, actualRes.first)
                assertEquals(expectRes.certificate, actualRes.second)

                printValue(expectRes.isoCode, actualRes.first)
                printValue(expectRes.certificate, actualRes.second)
            }
        }
        printDashes()
    }

    private fun <T> printValue(expectedValue: T, actualValue: T) =
        println(
            """
            >> $expectedValue (Expected)
            >> $actualValue (Actual)
            
        """.trimIndent()
        )

    private fun printDashes() =
        println("---------------------------")

    private fun <T> printHeader(headText: T, size: Int = ZERO_VALUE) =
        when (size) {
            ZERO_VALUE -> println("$headText")
            else -> println("$headText [Result: $size]")
        }
}