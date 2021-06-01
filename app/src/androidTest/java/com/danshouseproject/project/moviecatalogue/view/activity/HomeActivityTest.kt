package com.danshouseproject.project.moviecatalogue.view.activity

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.`object`.Genre
import com.danshouseproject.project.moviecatalogue.`object`.Movies
import com.danshouseproject.project.moviecatalogue.`object`.TvShows
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = Movies.generateMovies()
    private val dummyTvShows = TvShows.generateTvShows()
    private val dummyMoviesGenres = Genre.generateMoviesGenre()
    private val dummyTvShowsGenres = Genre.generateTvShowsGenre()

    private val resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources

    companion object {
        private const val VIEW_VISIBILITY_PERCENTAGE = 100
        private const val ZERO_VALUE = 0
        private const val ONE_VALUE = 1
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

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
        val finalZeroIndex = ZERO_VALUE

        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                finalZeroIndex, click()
            )
        )

        onView(allOf(withId(R.id.film_genre), isDisplayed())).check(matches(isDisplayed()))

        var moviesGenre = dummyMoviesGenres[finalZeroIndex].genre
        if (moviesGenre == null)
            moviesGenre = arrayListOf(ZERO_VALUE, ONE_VALUE)

        for (index in moviesGenre.indices) {
            onView(allOf(withId(R.id.film_genre), isDisplayed())).check(matches(isDisplayed()))
            onView(
                allOf(
                    withId(R.id.film_genre),
                    isDisplayed()
                )
            ).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    index,
                    click()
                )
            )
            onView(allOf(withId(R.id.film_genre))).check(
                matches(
                    atPosition(
                        index,
                        hasDescendant(withText(moviesGenre[index]))
                    )
                )
            )
        }
    }

    @Test
    fun tvShowsGenres_goToDetailSwipeLeftViewPagerThenCheckAndClickAtLastIndexTvShowsGenreEntities_returnTrueAndDisplayTvShowsGenreText() {
        val tvShowsLastIndex = dummyTvShowsGenres.size - ONE_VALUE

        onView(withId(R.id.view_pager)).perform(swipeLeft()).perform(swipeLeft())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                tvShowsLastIndex, click()
            )
        )

        onView(allOf(withId(R.id.film_genre), isDisplayed())).check(matches(isDisplayed()))

        var tvShowsGenre = dummyTvShowsGenres[tvShowsLastIndex].genre
        if (tvShowsGenre == null)
            tvShowsGenre = arrayListOf(ZERO_VALUE, ONE_VALUE)

        for (index in tvShowsGenre.indices) {
            onView(allOf(withId(R.id.film_genre), isDisplayed())).check(matches(isDisplayed()))
            onView(
                allOf(
                    withId(R.id.film_genre),
                    isDisplayed()
                )
            ).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    index,
                    click()
                )
            )
            onView(allOf(withId(R.id.film_genre))).check(
                matches(
                    atPosition(
                        index,
                        hasDescendant(withText(tvShowsGenre[index]))
                    )
                )
            )
        }
    }

    @Test
    fun loadMovies_checkEntitiesIsShowed_returnTrue() {
        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadTvShows_swipeLeftViewPagerAndCheckEntitiesIsShowed_returnTrue() {
        onView(withId(R.id.view_pager)).perform(swipeLeft()).perform(swipeLeft()).check(
            matches(
                isDisplayingAtLeast(
                    VIEW_VISIBILITY_PERCENTAGE
                )
            )
        )
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }

    @Test
    fun detailFirstMoviesEntities_checkEntitiesIsShowedUpAndClickEntitiesAtIndexZero_moveToDetailActivity() {
        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                ZERO_VALUE,
                click()
            )
        )
    }

    @Test
    fun detailLastTvShowsEntities_swipeLeftViewPagerAndCheckEntitiesIsShowedAndClickEntitiesAtLastIndex_moveToDetailActivity() {
        onView(withId(R.id.view_pager)).perform(swipeLeft()).perform(swipeLeft()).check(
            matches(
                isDisplayingAtLeast(
                    VIEW_VISIBILITY_PERCENTAGE
                )
            )
        )
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                dummyMovies.size - ONE_VALUE,
                click()
            )
        )
    }

    @Test
    fun checkDetailMoviesTitleEntities_moveToDetailWithZeroIndexEntitiesAndCheckMoviesTitleIsSameWithEntitiesTitle_returnTrue() {
        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(
            matches(
                isDisplayingAtLeast(
                    VIEW_VISIBILITY_PERCENTAGE
                )
            )
        )
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                ZERO_VALUE,
                click()
            )
        )

        onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_name_title)).check(matches(withText(resources.getString(dummyMovies[ZERO_VALUE].filmName))))
    }

    @Test
    fun checkDetailTvShowsTitleEntities_swipeLeftViewPagerAndMoveToDetailWithLastIndexEntitiesThenCheckTvShowsEntitiesTitleIsSameWithEntitiesTitle_returnTrue() {
        onView(withId(R.id.view_pager)).perform(swipeLeft()).perform(swipeLeft()).check(
            matches(
                isDisplayingAtLeast(
                    VIEW_VISIBILITY_PERCENTAGE
                )
            )
        )
        onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.rv_film),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                dummyTvShows.size - ONE_VALUE,
                click()
            )
        )

        onView(withId(R.id.film_name_title)).check(matches(isDisplayed()))
        onView(withId(R.id.film_name_title)).check(matches(withText(resources.getString(dummyTvShows[dummyTvShows.size - ONE_VALUE].filmName))))
    }

    @Test
    fun checkMoreDetailMoviesEntities_moveToDetailWithZeroIndexEntitiesAndCheckMoreDetailMoviesDataIsSameWithEntitiesDetailData_returnTrue() {
        onView(withId(R.id.view_pager)).perform(swipeRight())
            .check(matches(isDisplayingAtLeast(VIEW_VISIBILITY_PERCENTAGE)))

        dummyMovies[ZERO_VALUE].let {
            resources.apply {
                onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
                onView(
                    allOf(
                        withId(R.id.rv_film),
                        isDisplayed()
                    )
                ).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        ZERO_VALUE,
                        click()
                    )
                )

                onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
                onView(withId(R.id.film_rating)).check(matches(withText(getString(it.filmRatingSymbol))))

                onView(withId(R.id.film_duration)).check(matches(isDisplayed()))
                onView(withId(R.id.film_duration)).check(matches(withText(getString(it.filmDuration))))

                onView(withId(R.id.film_country_code)).check(matches(isDisplayed()))
                onView(withId(R.id.film_country_code)).check(matches(withText(getString(it.filmCountryCode))))

                onView(withId(R.id.film_release_date)).check(matches(isDisplayed()))
                onView(withId(R.id.film_release_date)).check(matches(withText(getString(it.filmReleaseDate))))

                onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score)).perform(click())

                onView(withId(R.id.film_score_value)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score_value)).check(
                    matches(
                        withText(
                            getString(
                                R.string.film_score_percent,
                                getInteger(it.filmScore)
                            )
                        )
                    )
                )

                onView(withId(R.id.film_overview)).check(matches(isDisplayed()))
                onView(withId(R.id.film_overview)).check(matches(withText(getString(it.filmOverview))))
            }
        }
    }

    @Test
    fun checkMoreDetailTvShowsEntities_swipeLeftViewPagerAndMoveToDetailWithLastIndexEntitiesAndCheckMoreDetailTvShowsDataIsSameWithEntitiesDetailData_returnTrue() {
        dummyTvShows[dummyTvShows.size - ONE_VALUE].let {
            resources.apply {
                onView(withId(R.id.view_pager)).perform(swipeLeft()).perform(swipeLeft()).check(
                    matches(
                        isDisplayingAtLeast(
                            VIEW_VISIBILITY_PERCENTAGE
                        )
                    )
                )
                onView(allOf(withId(R.id.rv_film), isDisplayed())).check(matches(isDisplayed()))
                onView(
                    allOf(
                        withId(R.id.rv_film),
                        isDisplayed()
                    )
                ).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        dummyTvShows.size - ONE_VALUE,
                        click()
                    )
                )

                onView(withId(R.id.film_rating)).check(matches(isDisplayed()))
                onView(withId(R.id.film_rating)).check(matches(withText(getString(it.filmRatingSymbol))))

                onView(withId(R.id.film_duration)).check(matches(isDisplayed()))
                onView(withId(R.id.film_duration)).check(matches(withText(getString(it.filmDuration))))

                onView(withId(R.id.film_country_code)).check(matches(isDisplayed()))
                onView(withId(R.id.film_country_code)).check(matches(withText(getString(it.filmCountryCode))))

                onView(withId(R.id.film_release_date)).check(matches(isDisplayed()))
                onView(withId(R.id.film_release_date)).check(matches(withText(getString(it.filmReleaseDate))))

                onView(withId(R.id.film_score)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score)).perform(click())

                onView(withId(R.id.film_score_value)).check(matches(isDisplayed()))
                onView(withId(R.id.film_score_value)).check(
                    matches(
                        withText(
                            getString(
                                R.string.film_score_percent,
                                getInteger(it.filmScore)
                            )
                        )
                    )
                )

                onView(withId(R.id.film_overview)).check(matches(isDisplayed()))
                onView(withId(R.id.film_overview)).check(matches(withText(getString(it.filmOverview))))
            }
        }
    }

}