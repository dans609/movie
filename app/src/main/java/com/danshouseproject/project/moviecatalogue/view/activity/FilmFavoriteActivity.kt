package com.danshouseproject.project.moviecatalogue.view.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivityFilmFavoriteBinding
import com.danshouseproject.project.moviecatalogue.databinding.ActivityHomeBinding
import com.danshouseproject.project.moviecatalogue.view.fragment.viewpager.FavoriteViewPager

class FilmFavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFilmFavoriteBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFilmFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        actionBarPref { supportActionBar }

        configViewPager()
    }

    private fun configViewPager() {
        val favoriteViewPager = FavoriteViewPager(this, supportFragmentManager)

        val viewPager = getHomeBinding { binding }?.viewPager
        viewPager?.adapter = favoriteViewPager

        val tabs = getHomeBinding { binding }?.tabLayout
        tabs?.setupWithViewPager(viewPager)
    }

    private inline fun getHomeBinding(
        view: () -> ActivityFilmFavoriteBinding?
    ): ActivityHomeBinding? =
        view()?.favoriteFilmLayout


    private inline fun actionBarPref(actionBar: () -> ActionBar?) =
        actionBar()?.let { actBar ->
            actBar.title = getString(R.string.film_favorite)
            actBar.elevation = resources.getInteger(R.integer.zero_value).toFloat()
            actBar.setDisplayHomeAsUpEnabled(true)
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}