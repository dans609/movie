package com.danshouseproject.project.moviecatalogue.view.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivityHomeBinding
import com.danshouseproject.project.moviecatalogue.view.favorite.FilmFavoriteActivity
import com.danshouseproject.project.moviecatalogue.view.home.branch.SectionsViewPager

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.elevation = resources.getInteger(R.integer.zero_value).toFloat()

        SectionsViewPager(this, supportFragmentManager).let { sectionViewPager ->
            with(binding as ActivityHomeBinding) {
                val viewPager = viewPager
                viewPager.adapter = sectionViewPager

                val tabs = tabLayout
                tabs.setupWithViewPager(viewPager)
            }
        }
    }

    private fun Menu?.findMenuIco(id: Int): Drawable =
        this?.findItem(id)?.icon as Drawable

    private fun drawableTint(vararg icon: Drawable, color: () -> Int) =
        icon.forEach { ico ->
            DrawableCompat.setTint(ico, color())
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuFavoriteIcon = menu.findMenuIco(R.id.see_film_favorite)
        val getRedColor = ContextCompat.getColor(this, R.color.colorRed)

        drawableTint(menuFavoriteIcon) { getRedColor }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language_preferences -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.see_film_favorite -> startActivity(Intent(this, FilmFavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}