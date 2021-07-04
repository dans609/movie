package com.danshouseproject.project.moviecatalogue.core.components.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.danshouseproject.project.moviecatalogue.utils.dummy.Genre
import com.danshouseproject.project.moviecatalogue.utils.dummy.Info
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.LiveDataTestUtils
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.utils.dummy.test.constant.Constant
import com.danshouseproject.project.moviecatalogue.core.components.data.local.LocalDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.RemoteDataSource
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseMovies
import com.danshouseproject.project.moviecatalogue.core.components.data.remote.response.ResponseTvShows
import com.danshouseproject.project.moviecatalogue.core.utils.AppExecutors
import com.danshouseproject.project.moviecatalogue.core.utils.PagedListUtil
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieCatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val fakeRepository = FakeMovieCatalogueRepository(remote, local, appExecutors)


    @Test
    fun getAllMovies() {
        val dummyMovies = castResponseMovieToModel(remoteMovies)
        val listMovies = ArrayList<ListFilm>()
        val listResponseMovies = MutableLiveData<List<ListFilm>>()
        listResponseMovies.value = dummyMovies

        for (index in dummyMovies.indices) {
            val modelObject = dummyMovies[index]
            val responseMovies = MutableLiveData<ListFilm>()
            responseMovies.value = modelObject

            `when`(local.getFilm(modelObject.filmId, true)).thenReturn(responseMovies)
            val moviesEntity =
                LiveDataTestUtils.getValue(fakeRepository.getAllMovies(modelObject.filmId))
            verify(local).getFilm(modelObject.filmId, true)

            moviesEntity.let {
                modelObject.also { expected ->
                    assertNotNull(it)
                    assertNotNull(it.data)
                    assertNotNull(it.data?.filmName)
                    assertNull(it.message)
                    assertTrue(it.data?.isMovies ?: false)
                    assertFalse(it.data?.isOnFavorite ?: false)
                    assertEquals(expected.filmId, it.data?.filmId)
                    assertEquals(expected.filmScore, it.data?.filmScore)
                    assertEquals(expected.isMovies, it.data?.isMovies)
                    assertEquals(expected.filmName, it.data?.filmName)
                    assertEquals(expected.filmImage, it.data?.filmImage)
                    listMovies.add(it.data as ListFilm)
                }
            }
        }
        assertEquals(listResponseMovies.value?.size, listMovies.size)
    }


    @Test
    fun getAllTvShows() {
        val dummyTv = castResponseTvToModel(remoteTvShows)
        val listTv = ArrayList<ListFilm>()
        val listResponseTv = MutableLiveData<List<ListFilm>>()
        listResponseTv.value = dummyTv

        for (index in dummyTv.indices) {
            val modelObject = dummyTv[index]
            val responseTv = MutableLiveData<ListFilm>()
            responseTv.value = modelObject

            `when`(local.getFilm(modelObject.filmId, false)).thenReturn(responseTv)
            val tvEntity =
                LiveDataTestUtils.getValue(fakeRepository.getAllTvShows(modelObject.filmId))
            verify(local).getFilm(modelObject.filmId, false)

            tvEntity.let {
                modelObject.also { expected ->
                    assertNotNull(it)
                    assertNotNull(it.data)
                    assertNotNull(it.data?.filmName)
                    assertNull(it.message)
                    assertFalse(it.data?.isMovies ?: false)
                    assertFalse(it.data?.isOnFavorite ?: false)
                    assertEquals(expected.filmId, it.data?.filmId)
                    assertEquals(expected.filmScore, it.data?.filmScore)
                    assertEquals(expected.isMovies, it.data?.isMovies)
                    assertEquals(expected.filmName, it.data?.filmName)
                    assertEquals(expected.filmImage, it.data?.filmImage)
                    listTv.add(it.data as ListFilm)
                }
            }
        }
        assertEquals(listResponseTv.value?.size, listTv.size)
    }


    @Test
    fun getMoviesGenres() {
        val dummyRemoteMovies = castResponseMovieToModel(remoteMovies)
        val dummyGenre = dummyMoviesGenres
        val arrayGenre = ArrayList<FilmGenre>()

        for (index in dummyRemoteMovies.indices) {
            for (idxGenre in dummyGenre.indices) {
                if (dummyRemoteMovies[index].filmId == dummyGenre[idxGenre].filmId) {
                    val modelObject = dummyGenre[idxGenre]
                    val responseGenre = MutableLiveData<FilmGenre>()
                    responseGenre.value = modelObject
                    arrayGenre.add(modelObject)

                    `when`(local.getFilmGenre(modelObject.filmId, true)).thenReturn(responseGenre)
                    val genreEntity =
                        LiveDataTestUtils.getValue(fakeRepository.getMoviesGenres(modelObject.filmId))
                    verify(local).getFilmGenre(modelObject.filmId, true)

                    genreEntity.let {
                        modelObject.also { expected ->
                            assertNotNull(it)
                            assertNotNull(it.data)
                            assertNotNull(it.data?.genre)
                            assertNull(it.message)

                            assertTrue(it.data?.isMovies ?: false)
                            assertNotEquals(ZERO_VALUE, it.data?.filmId)
                            assertNotEquals(ZERO_VALUE, it.data?.genre?.size)

                            val actualGenreList = it.data?.genre ?: return
                            val listGenre = ArrayList<String?>()

                            for (idx in actualGenreList.indices) {
                                listGenre.add(getGenreValue(actualGenreList[idx].genre))
                                val expString = getGenreValue(expected.genre?.get(idx)?.genre)
                                val actString = getGenreValue(actualGenreList[idx].genre)
                                assertEquals(expString, actString)
                            }

                            assertEquals(expected.filmId, it.data?.filmId)
                            assertEquals(expected.isMovies, it.data?.isMovies)
                            assertEquals(expected.genre, actualGenreList)
                            assertEquals(expected.genre?.size, actualGenreList.size)
                        }
                    }
                    break
                } else continue
            }
        }
        assertEquals(expectedDataSize, arrayGenre.size)
    }


    @Test
    fun getTvGenres() {
        val dummyRemoteTv = castResponseTvToModel(remoteTvShows)
        val dummyGenre = dummyTvShowsGenres
        val arrayGenre = ArrayList<FilmGenre>()
        arrayGenre.addAll(dummyGenre)

        for (index in dummyRemoteTv.indices) {
            for (idxGenre in dummyGenre.indices) {
                if (dummyRemoteTv[index].filmId == dummyGenre[idxGenre].filmId) {
                    val modelObject = dummyGenre[idxGenre]
                    val responseGenre = MutableLiveData<FilmGenre>()
                    responseGenre.value = modelObject

                    `when`(local.getFilmGenre(modelObject.filmId, false)).thenReturn(responseGenre)
                    val genreEntity =
                        LiveDataTestUtils.getValue(fakeRepository.getTvGenres(modelObject.filmId))
                    verify(local).getFilmGenre(modelObject.filmId, false)

                    genreEntity.let {
                        modelObject.also { expected ->
                            assertNotNull(it)
                            assertNotNull(it.data)
                            assertNotNull(it.data?.genre)
                            assertNull(it.message)

                            assertFalse(it.data?.isMovies ?: false)
                            assertNotEquals(ZERO_VALUE, it.data?.filmId)
                            assertNotEquals(ZERO_VALUE, it.data?.genre?.size)

                            val actualGenreList = it.data?.genre ?: return
                            val listGenre = ArrayList<String?>()

                            for (idx in actualGenreList.indices) {
                                listGenre.add(getGenreValue(actualGenreList[idx].genre))
                                val expString = getGenreValue(expected.genre?.get(idx)?.genre)
                                val actString = getGenreValue(actualGenreList[idx].genre)
                                assertEquals(expString, actString)
                            }

                            assertEquals(expected.filmId, it.data?.filmId)
                            assertEquals(expected.isMovies, it.data?.isMovies)
                            assertEquals(expected.genre, actualGenreList)
                            assertEquals(expected.genre?.size, actualGenreList.size)
                        }
                    }
                    break
                } else continue
            }
        }
        assertEquals(expectedDataSize, arrayGenre.size)
    }


    @Test
    fun getMoviesMoreInfo() {
        val dummyMoviesInfo = getMoviesInfo
        val arrayMoviesInfo = ArrayList<FilmInfo>()
        arrayMoviesInfo.addAll(dummyMoviesInfo)

        for (index in dummyMoviesInfo.indices) {
            val iterateObject = dummyMoviesInfo[index]
            val responseFilmInfo = MutableLiveData<FilmInfo>()
            responseFilmInfo.value = iterateObject

            `when`(local.getFilmInfo(iterateObject.filmId, true)).thenReturn(responseFilmInfo)
            val filmInfoEntity =
                LiveDataTestUtils.getValue(fakeRepository.getMoviesMoreInfo(iterateObject.filmId))
            verify(local).getFilmInfo(iterateObject.filmId, true)

            filmInfoEntity.let { actualData ->
                iterateObject.also { expectedData ->
                    assertNull(actualData.message)
                    assertNotNull(actualData.data)
                    assert(!(actualData.data?.filmRating?.trimIndent().isNullOrEmpty()))
                    assert(!(actualData.data?.isoCode?.trimIndent().isNullOrEmpty()))
                    assertTrue(actualData.data?.isMovies ?: false)
                    assertNotEquals(ZERO_VALUE, actualData.data?.filmId)
                    assertEquals(index, actualData.data?.id)
                    assertEquals(expectedData.filmId, actualData.data?.filmId)
                    assertEquals(expectedData.isMovies, actualData.data?.isMovies ?: false)
                    assertEquals(expectedData.filmRating, actualData.data?.filmRating)
                    assertEquals(expectedData.isoCode, actualData.data?.isoCode)
                }
            }
        }
        assertEquals(expectedDataSize, arrayMoviesInfo.size)
    }


    @Test
    fun getTvMoreInfo() {
        val dummyTvShowsInfo = getTvShowsInfo
        val arrayTvShowsInfo = ArrayList<FilmInfo>()
        arrayTvShowsInfo.addAll(dummyTvShowsInfo)

        for (index in dummyTvShowsInfo.indices) {
            val iterateObject = dummyTvShowsInfo[index]
            val responseFilmInfo = MutableLiveData<FilmInfo>()
            responseFilmInfo.value = iterateObject

            `when`(local.getFilmInfo(iterateObject.filmId, false)).thenReturn(responseFilmInfo)
            val filmInfoEntity =
                LiveDataTestUtils.getValue(fakeRepository.getTvMoreInfo(iterateObject.filmId))
            verify(local).getFilmInfo(iterateObject.filmId, false)

            filmInfoEntity.let { actualData ->
                iterateObject.also { expectedData ->
                    assertNull(actualData.message)
                    assertNotNull(actualData.data)
                    assert(!(actualData.data?.filmRating?.trimIndent().isNullOrEmpty()))
                    assert(!(actualData.data?.isoCode?.trimIndent().isNullOrEmpty()))
                    assertFalse(actualData.data?.isMovies ?: false)
                    assertNotEquals(ZERO_VALUE, actualData.data?.filmId)
                    assertEquals(index, actualData.data?.id)
                    assertEquals(expectedData.filmId, actualData.data?.filmId)
                    assertEquals(expectedData.isMovies, actualData.data?.isMovies ?: false)
                    assertEquals(expectedData.filmRating, actualData.data?.filmRating)
                    assertEquals(expectedData.isoCode, actualData.data?.isoCode)
                }
            }
        }
        assertEquals(expectedDataSize, arrayTvShowsInfo.size)
    }


    @Suppress("UNCHECKED_CAST")
    @Test
    fun getAllFavoriteFilm() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteFilm>

        val listBool = listOf(true, false)
        for (boolVal in listBool) {
            `when`(local.getAllFavoriteFilm(boolVal)).thenReturn(dataSourceFactory)
            fakeRepository.getAllFavoriteFilm(boolVal)

            val listEntity = listOf(remoteMovies, remoteTvShows)
            val condition = if (boolVal) listEntity[ZERO_VALUE] else listEntity[ONE_VALUE]

            if (boolVal) {
                val favoriteEntity = Resource.success(PagedListUtil.mockPagedList(condition))
                verify(local).getAllFavoriteFilm(boolVal)

                assertNotNull(favoriteEntity)
                assertEquals(condition.size, favoriteEntity.data?.size)
            }
        }
    }

    private fun generateFilmDuration(duration: Int) =
        fakeRepository.generateFilmDuration(duration)

    private fun convertFilmScore(score: Double): Int =
        fakeRepository.convertFilmScore(score)

    private fun castResponseMovieToModel(response: List<ResponseMovies>): List<ListFilm> {
        val containerList = ArrayList<ListFilm>()
        response.indices.forEach { idx ->
            response[idx].let { data ->
                val getDuration = data.moviesDuration
                val instance = ListFilm(
                    filmId = data.moviesId,
                    filmName = data.moviesTitle,
                    filmDuration = generateFilmDuration(getDuration),
                    filmReleaseDate = data.moviesReleaseDate,
                    filmOverview = data.moviesOverView,
                    filmImage = data.moviesPosterPath,
                    filmScore = convertFilmScore(data.moviesScore),
                    isMovies = true
                )
                containerList.add(instance)
            }
        }
        return containerList
    }


    private fun castResponseTvToModel(response: List<ResponseTvShows>): List<ListFilm> {
        val containerList = ArrayList<ListFilm>()
        response.indices.forEach { idx ->
            response[idx].let { data ->
                val getDuration = data.tvDuration?.get(ZERO_VALUE) ?: ZERO_VALUE
                val instance = ListFilm(
                    filmId = data.tvId,
                    filmName = data.tvTitle,
                    filmDuration = generateFilmDuration(getDuration),
                    filmReleaseDate = data.tvReleaseDate,
                    filmOverview = data.tvOverview,
                    filmImage = data.tvPosterPath,
                    filmScore = convertFilmScore(data.tvScore),
                    isMovies = false
                )
                containerList.add(instance)
            }
        }
        return containerList
    }


    companion object {
        private const val ZERO_VALUE = 0
        private const val ONE_VALUE = 1
        private const val expectedDataSize = 19

        private val remoteMovies get() = RemoteMovies.generateMoviesResponse()
        private val remoteTvShows get() = RemoteTvShows.generateTvShowsResponse()
        private val dummyMoviesGenres get() = Genre.generateMoviesGenre()
        private val dummyTvShowsGenres get() = Genre.generateTvShowsGenre()
        private val getAllGenres get() = Constant.getAllGenre
        private val getMoviesInfo get() = Info.generateMoviesInfo()
        private val getTvShowsInfo get() = Info.generatetvShowsInfo()

        @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
        private fun getGenreValue(genreId: String?): String? =
            getAllGenres[genreId ?: "0".toInt()]
    }
}