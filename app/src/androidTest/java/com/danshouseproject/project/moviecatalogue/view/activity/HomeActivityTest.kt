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
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteAdditionalData
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteMovies
import com.danshouseproject.project.moviecatalogue.`object`.test.RemoteTvShows
import com.danshouseproject.project.moviecatalogue.data.remote.response.FetchFilmGenres
import com.danshouseproject.project.moviecatalogue.helper.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    companion object {
        private const val VIEW_VISIBILITY_PERCENTAGE = 100
        private const val ZERO_VALUE = 0
        private const val ONE_VALUE = 1
        private const val TEEN_VALUE = 10
    }

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
    fun moviesGenres_goToDetailThenCheckAndClickAtFirstIndexMoviesGenreEntities_returnTrueAndDisplayMoviesGenreText() {
        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )
        onView(allOf(withId(R.id.film_genre), isDisplayed()))
            .check(matches(isDisplayed()))

        for (index in dummyRemoteMovies.indices) {
            val movieName = getText(withId(R.id.film_name_title))
            if (dummyRemoteMovies[index].moviesTitle == movieName) {
                val moviesGenres = dummyRemoteMovies[index].moviesGenres as List<FetchFilmGenres>

                for (idxGenre in moviesGenres.indices) {
                    onView(
                        allOf(
                            withId(R.id.film_genre),
                            isDisplayed()
                        )
                    ).check(matches(isDisplayed()))
                    onView(allOf(withId(R.id.film_genre), isDisplayed()))
                        .perform(
                            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                idxGenre,
                                click()
                            )
                        )
                    onView(allOf(withId(R.id.film_genre)))
                        .check(
                            matches(
                                atPosition(
                                    idxGenre,
                                    hasDescendant(withText(moviesGenres[idxGenre].genre))
                                )
                            )
                        )
                }

                break
            } else continue
        }
    }

    @Test
    fun tvShowsGenres_goToDetailSwipeLeftViewPagerThenCheckAndClickAtLastIndexTvShowsGenreEntities_returnTrueAndDisplayTvShowsGenreText() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
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
            if (dummyRemoteTv[index].tvTitle == tvName) {
                val tvGenres = dummyRemoteTv[index].tvGenres as List<FetchFilmGenres>

                for (idxGenre in tvGenres.indices) {
                    onView(allOf(withId(R.id.film_genre), isDisplayed()))
                        .check(matches(isDisplayed()))
                    onView(allOf(withId(R.id.film_genre), isDisplayed()))
                        .perform(
                            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                idxGenre,
                                click()
                            )
                        )
                    onView(allOf(withId(R.id.film_genre)))
                        .check(
                            matches(
                                atPosition(
                                    idxGenre,
                                    hasDescendant(withText(tvGenres[idxGenre].genre))
                                )
                            )
                        )
                }

                break
            } else continue
        }
    }

    @Test
    fun loadMovies_checkEntitiesIsShowed_returnTrue() {
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
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyRemoteTv.lastIndex))
    }

    @Test
    fun detailFirstMoviesEntities_checkEntitiesIsShowedUpAndClickEntitiesAtIndexZero_moveToDetailActivity() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )
    }

    @Test
    fun detailLastTvShowsEntities_swipeLeftViewPagerAndCheckEntitiesIsShowedAndClickEntitiesAtLastIndex_moveToDetailActivity() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    dummyRemoteTv.lastIndex,
                    click()
                )
            )
    }

    @Test
    fun checkDetailMoviesTitleEntities_moveToDetailWithZeroIndexEntitiesAndCheckMoviesTitleIsSameWithEntitiesTitle_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )
        onView(withId(R.id.film_name_title))
            .check(matches(isDisplayed()))

        val movieTitle = getText(withId(R.id.film_name_title))
        for (index in dummyRemoteMovies.indices)
            if (dummyRemoteMovies[index].moviesTitle == movieTitle) {
                onView(withId(R.id.film_name_title)).check(matches(withText(dummyRemoteMovies[index].moviesTitle)))
                break
            } else continue
    }


    @Test
    fun checkDetailTvShowsTitleEntities_swipeLeftViewPagerAndMoveToDetailWithLastIndexEntitiesThenCheckTvShowsEntitiesTitleIsSameWithEntitiesTitle_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    dummyRemoteTv.size - ONE_VALUE,
                    click()
                )
            )
        onView(withId(R.id.film_name_title))
            .check(matches(isDisplayed()))

        val tvTitle = getText(withId(R.id.film_name_title))
        for (index in dummyRemoteTv.indices) {
            if (dummyRemoteTv[index].tvTitle == tvTitle) {
                onView(withId(R.id.film_name_title)).check(matches(withText(dummyRemoteTv[index].tvTitle)))
                break
            } else continue
        }
    }

    @Test
    fun checkMoreDetailMoviesEntities_moveToDetailWithZeroIndexEntitiesAndCheckMoreDetailMoviesDataIsSameWithEntitiesDetailData_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ZERO_VALUE,
                    click()
                )
            )

        val movieTitle = getText(withId(R.id.film_name_title))
        for (index in dummyRemoteMovies.indices) {
            val responseMovies = dummyRemoteMovies[index]
            if (dummyRemoteMovies[index].moviesTitle == movieTitle) {
                val additionalData =
                    RemoteAdditionalData.generateMoviesAdditionalDataResponse(responseMovies.moviesId)

                onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
                onView(withId(R.id.film_rating)).check(
                    matches(
                        withText(
                            additionalData.result?.get(ZERO_VALUE)?.moviesCertificate?.get(
                                ZERO_VALUE
                            )?.certificate.toString()
                        )
                    )
                )

                onView(withId(R.id.film_country_code)).check(matches(isDisplayed()))
                onView(withId(R.id.film_country_code)).check(
                    matches(
                        withText(
                            additionalData.result?.get(
                                ZERO_VALUE
                            )?.isoCode.toString()
                        )
                    )
                )

                onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score)).perform(click())

                val score = (responseMovies.moviesScore * TEEN_VALUE).toInt()
                onView(withId(R.id.film_score_value))
                    .check(matches(isDisplayed()))
                onView(withId(R.id.film_score_value))
                    .check(
                        matches(
                            withText(
                                resources.getString(
                                    R.string.film_score_percent,
                                    score
                                )
                            )
                        )
                    )

                break
            } else continue
        }
    }

    @Test
    fun checkMoreDetailTvShowsEntities_swipeLeftViewPagerAndMoveToDetailWithLastIndexEntitiesAndCheckMoreDetailTvShowsDataIsSameWithEntitiesDetailData_returnTrue() {
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
            .perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed()))
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

                onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score)).perform(click())

                val score = (responseTv.tvScore * TEEN_VALUE).toInt()
                onView(withId(R.id.film_score_value))
                    .check(matches(isDisplayed()))
                onView(withId(R.id.film_score_value))
                    .check(
                        matches(
                            withText(
                                resources.getString(
                                    R.string.film_score_percent,
                                    score
                                )
                            )
                        )
                    )

                break
            } else continue
        }
    }

    private fun getText(matcher: Matcher<View?>?): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[ZERO_VALUE] = tv.text.toString()
            }
        })
        return stringHolder[ZERO_VALUE]
    }

}