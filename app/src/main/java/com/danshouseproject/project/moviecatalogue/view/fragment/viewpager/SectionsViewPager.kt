package com.danshouseproject.project.moviecatalogue.view.fragment.viewpager

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.view.fragment.MoviesFragment
import com.danshouseproject.project.moviecatalogue.view.fragment.TvShowsFragment

class SectionsViewPager(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies_title, R.string.tv_shows_title)
    }

    override fun getCount(): Int = context.resources.getInteger(R.integer.int_2)

    override fun getItem(position: Int): Fragment =
        context.resources.let { rsc ->
            when (position) {
                rsc.getInteger(R.integer.zero_value) -> MoviesFragment()
                rsc.getInteger(R.integer.int_1) -> TvShowsFragment()
                else -> Fragment()
            }
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLES[position])

}