package com.danshouseproject.project.moviecatalogue.view.activity

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.utils.`object`.test.RemoteAdditionalData
import com.danshouseproject.project.moviecatalogue.utils.`object`.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.utils.`object`.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.utils.EspressoIdlingResource
import com.danshouseproject.project.moviecatalogue.view.home.HomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {
    private lateinit var resources: Resources
    private val dummyRemoteMovies = RemoteMovies.generateMoviesResponse()
    private val dummyRemoteTv = RemoteTvShows.generateTvShowsResponse()

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        IdlingRegistry.getInstance()
            .register(EspressoIdlingResource.getEspressoIdlingResourceForActivity())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance()
            .unregister(EspressoIdlingResource.getEspressoIdlingResourceForActivity())
    }

    private fun getText(matcher: Matcher<View?>?): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> =
                isAssignableFrom(TextView::class.java)

            override fun getDescription(): String =
                "getting text from a TextView"

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[ZERO_VALUE] = tv.text.toString()
            }
        })
        return stringHolder[ZERO_VALUE]
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText(position.toString())
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }


    @Test
    fun moviesGenres_checkAndClickMoviesAtTheFirstIndex_moveToDetailPageThenCheckAndClickListMoviesGenreAlsoCheckTitleAndGenreTextIsMatchessWithTheCondition() {
        onView(withId(R.id.view_pager)).perform(swipeRight()).check(
            matches(
                isDisplayingAtLeast(
                    VIEW_VISIBILITY_PERCENTAGE
                )
            )
        )
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )
        onView(allOf(withId(R.id.film_genre), isDisplayed())).check(matches(isDisplayed()))

        for (index in dummyRemoteMovies.indices) {
            val movieName = getText(withId(R.id.film_name_title))
            val dummyRemote = dummyRemoteMovies[index]

            if (dummyRemote.moviesTitle == movieName) {
                val moviesGenres = dummyRemote.moviesGenres
                for (idxGenre in moviesGenres?.indices ?: return) {
                    onView(allOf(withId(R.id.film_genre), isDisplayed()))
                        .check(matches(isDisplayed()))
                        .perform(
                            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                idxGenre,
                                click()
                            )
                        )
                    onView(allOf(withId(R.id.film_genre))).check(
                        matches(
                            atPosition(
                                idxGenre, hasDescendant(
                                    withText(
                                        moviesGenres[idxGenre].genre
                                    )
                                )
                            )
                        )
                    )
                }
                break
            } else continue
        }
    }


    @Test
    fun tvShowsGenres_SwipeViewPagerToLeftThenCheckAndClickTvShowsAtTheLastIndex_returnTrueAndMoveToDetailThenCheckAndClickListTvGenreAlsoCheckTitleAndGenreTextAreMatchess() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    dummyRemoteTv.lastIndex,
                    click()
                )
            )
        onView(allOf(withId(R.id.film_genre), isDisplayed()))
            .check(matches(isDisplayed()))

        for (index in dummyRemoteTv.indices) {
            val tvName = getText(withId(R.id.film_name_title))
            val dummyRemote = dummyRemoteTv[index]

            if (dummyRemote.tvTitle == tvName) {
                val tvGenres = dummyRemote.tvGenres
                for (idxGenre in tvGenres?.indices ?: return) {
                    onView(allOf(withId(R.id.film_genre), isDisplayed()))
                        .check(matches(isDisplayed()))
                        .perform(
                            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                idxGenre,
                                click()
                            )
                        )
                    onView(allOf(withId(R.id.film_genre))).check(
                        matches(
                            atPosition(
                                idxGenre, hasDescendant(
                                    withText(
                                        tvGenres[idxGenre].genre
                                    )
                                )
                            )
                        )
                    )
                }
                break
            } else continue
        }
    }


    @Test
    fun loadMovies_checkAllMovieEntitiesAlreadyShowed_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyRemoteMovies.size))
    }


    @Test
    fun loadTvShows_swipeLeftViewPagerAndCheckEntitiesIsShowed_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyRemoteTv.lastIndex))
    }


    @Test
    fun moveToDetailMovies_checkAndClickMoviesAtTheFirstIndex_returnTrueAndMoveToDetailOfMovies() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )
    }


    @Test
    fun moveToDetailTv_swipeLeftViewPagerThenCheckAndClickMoviesAtTheLastIndex_returnTrueAndMoveToDetailOfTv() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    dummyRemoteTv.lastIndex,
                    click()
                )
            )
    }


    @Test
    fun moreMoviesDetail_checkAndClickMoviesAtFirstIndexThenMoveToDetail_returnTrueAndCheckMoviesMoreDetailIsShown() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )

        val movieTitle = getText(withId(R.id.film_name_title))
        for (index in dummyRemoteMovies.indices) {
            val responseMovies = dummyRemoteMovies[index]

            if (responseMovies.moviesTitle == movieTitle) {
                val additionalData = moviesMoreData(responseMovies.moviesId)
                additionalData.result?.get(ZERO_VALUE)?.let {
                    val movieCertif = it.moviesCertificate?.get(ZERO_VALUE)
                    val movieIso = it.isoCode
                    val score = (responseMovies.moviesScore * TEEN_VALUE).toInt()
                    val getFormatScore = resources.getString(R.string.film_score_percent, score)

                    onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_name_title)).check(matches(withText(responseMovies.moviesTitle)))

                    onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_rating)).check(matches(withText(movieCertif?.certificate)))

                    onView(withId(R.id.film_country_code)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_country_code)).check(matches(withText(movieIso)))

                    onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_score)).perform(click())

                    onView(withId(R.id.film_score_value)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_score_value)).check(matches(withText(getFormatScore)))
                }
                break
            } else continue
        }
    }


    @Test
    fun moreTvDetail_checkAndClickTvAtTheLastIndexThenMoveToDetail_returnTrueAndCheckTvMoreDetailIsShown() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    dummyRemoteTv.lastIndex,
                    click()
                )
            )

        val tvTitle = getText(withId(R.id.film_name_title))
        for (index in dummyRemoteTv.indices) {
            val responseTv = dummyRemoteTv[index]
            if (responseTv.tvTitle == tvTitle) {
                val additionalData = tvShowsMoreData(responseTv.tvId)
                additionalData.result?.get(ZERO_VALUE).let {
                    val tvCertificate = it?.certificate
                    val tvIso = it?.isoCode
                    val score = (responseTv.tvScore * TEEN_VALUE).toInt()
                    val getFormatScore = resources.getString(R.string.film_score_percent, score)

                    onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_name_title)).check(matches(withText(responseTv.tvTitle)))

                    onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_score)).perform(click())

                    onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_rating)).check(matches(withText(tvCertificate)))

                    onView(withId(R.id.film_country_code)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_country_code)).check(matches(withText(tvIso)))

                    onView(withId(R.id.film_score_value)).check(matches(isDisplayed()))
                    onView(withId(R.id.film_score_value)).check(matches(withText(getFormatScore)))
                }
                break
            } else continue
        }
    }


    @Test
    fun addMoviesToFavorite_checkAndClickFirstMoviesAndGoToDetailThenClickHearthButtonBackToHomeAndClickHearthPage_moveToHearthPageAndMoviesIsDisplayed() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ZERO_VALUE, click()))
        onView(withId(R.id.favorite_film_fab))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.see_film_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_favorite_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ZERO_VALUE, click()))
        onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.film_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_film_fab))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(isRoot()).perform(pressBack())
    }


    @Test
    fun addTvsToFavorite_checkAndClickLastTvAndGoToDetailThenClickHearthButtonBackToHomeAndClickHearthPage_moveToHearthPageAndTvIsDisplayed() {
        val lastIndex = dummyRemoteTv.lastIndex
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(lastIndex, click()))
        onView(withId(R.id.favorite_film_fab))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.see_film_favorite))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_favorite_film), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ZERO_VALUE, click()))
        onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.film_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_film_fab))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(isRoot()).perform(pressBack())
    }


    companion object {
        private const val VIEW_VISIBILITY_PERCENTAGE = 100
        private const val ZERO_VALUE = 0
        private const val TEEN_VALUE = 10

        private fun moviesMoreData(filmId: Int) =
            RemoteAdditionalData.generateMoviesAdditionalDataResponse(filmId)

        private fun tvShowsMoreData(filmId: Int) =
            RemoteAdditionalData.generateTvsAdditionalDataResponse(filmId)
    }
}