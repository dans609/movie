package com.danshouseproject.project.moviecatalogue.view.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivityHomeBinding
import com.danshouseproject.project.moviecatalogue.di.Injection
import com.danshouseproject.project.moviecatalogue.view.fragment.viewpager.SectionsViewPager

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsViewPager = SectionsViewPager(this, supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsViewPager

        val tabs = binding.tabLayout
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = resources.getInteger(R.integer.zero_value).toFloat()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language_preferences -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }

}