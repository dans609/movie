package com.danshouseproject.project.moviecatalogue.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.danshouseproject.project.moviecatalogue.databinding.DisplayDetailUserInterfaceBinding
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.adapter.GenreAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.GenreViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_film.*

class DetailFilmActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailFilmBinding
    private lateinit var bindingDisplay: DisplayDetailUserInterfaceBinding
    private lateinit var genreAdapter: GenreAdapter
    private var statusFavorite: Boolean = false
    private val detailPageTitle = "Detail Film"
    private var moviesPosition = 0
    private var tvShowsPosition = 0
    private var filmId: Int = 0

    companion object {
        const val EXTRA_FILM = "extra_film"
        const val EXTRA_MOVIES_POSITION = "extra_movies_position"
        const val EXTRA_TV_SHOWS_POSITION = "extra_tv_shows_position"
        const val EXTRA_FLAG = "extra_flag"
        private lateinit var context: DetailFilmActivity
        private lateinit var onclick: View.OnClickListener
        private val nullValue = null

        private fun setContext(context: DetailFilmActivity) =
            context.let {
                this.context = it
                this.onclick = it
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        bindingDisplay = binding.detailFilm
        setContentView(binding.root)
        setContext(this)
        actionBarPreference(detailPageTitle)

        genreAdapter = GenreAdapter()
        moviesPosition =
            intent.getIntExtra(EXTRA_MOVIES_POSITION, resources.getInteger(R.integer.zero_value))
        tvShowsPosition =
            intent.getIntExtra(EXTRA_TV_SHOWS_POSITION, resources.getInteger(R.integer.zero_value))

        val getIntent = intent.getParcelableExtra<ListFilm>(EXTRA_FILM) as ListFilm
        val condition = intent.getIntExtra(EXTRA_FLAG, resources.getInteger(R.integer.zero_value))

        Glide.with(this)
            .asBitmap()
            .load(getIntent.filmImage.toInt())
            .apply(RequestOptions().placeholder(R.drawable.splash_screen_image).override(resources.getInteger(R.integer.int_200), resources.getInteger(R.integer.int_300)))
            .into(bindingDisplay.filmImage)

        with(bindingDisplay) {
            getIntent.let {
                filmId = it.filmId
                filmNameTitle.text = getString(it.filmName.toInt())
                filmRating.text = it.filmRatingSymbol.toString()
                filmDuration.text = getString(it.filmDuration.toInt())
                filmCountryCode.text = it.filmCountryCode.toString()
                filmReleaseDate.text = getString(it.filmReleaseDate.toInt())
                filmOverview.text = getString(it.filmOverview.toInt())
                setScoreDrawable(resources.getInteger(it.filmScore))

                // getString(it.filmRatingSymbol.toInt())
                // getString(it.filmCountryCode.toInt())
            }
        }

        getViewModel(condition)
        recyclerConfig()
        countOverviewLines()

        binding.favoriteFilmFab.setOnClickListener(this)
        bindingDisplay.filmScore.setOnClickListener(this)
    }

    private fun recyclerConfig() =
        bindingDisplay.filmGenre.let { rv ->
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = genreAdapter
        }

    private fun getViewModel(flag: Int) {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory).get(GenreViewModel::class.java)
        when (flag) {
            resources.getInteger(R.integer.movies_flag) -> {
                viewModel.getMoviesGenres(filmId).observe(this, { listGenre ->
                    if (listGenre != null)
                        genreAdapter.setListGenre(listGenre.genre)
                    else return@observe

                })
            }
            resources.getInteger(R.integer.tv_shows_flag) -> {
                viewModel.getTvShowsGenres(filmId).observe(this, { listGenre ->
                    if (listGenre != null)
                        genreAdapter.setListGenre(listGenre.genre)
                    else return@observe
                })
            }
        }
    }

    private fun actionBarPreference(title: String) =
        supportActionBar.let {
            it?.setDisplayHomeAsUpEnabled(true)
            if (it == null) it?.title = getString(R.string.detail_film_page_title)
            else it.title = title
        }

    private fun setScoreDrawable(score: Int) =
        bindingDisplay.let {
            var scoreInRange = true
            when (score) {
                in resources.getInteger(R.integer.score_value70)..resources.getInteger(R.integer.max_score_range) -> it.filmScore.progressDrawable =
                    ContextCompat.getDrawable(context, R.drawable.ic_circular_progress_bar)
                in resources.getInteger(R.integer.min_score_range_in_medium_rate)..resources.getInteger(
                    R.integer.score_value69
                ) -> it.filmScore.progressDrawable =
                    ContextCompat.getDrawable(context, R.drawable.ic_circular_medium_film_score)
                in resources.getInteger(R.integer.zero_value)..resources.getInteger(R.integer.max_score_range_in_bad_rate) -> it.filmScore.progressDrawable =
                    ContextCompat.getDrawable(context, R.drawable.ic_circular_bad_film_score)
                else -> {
                    scoreInRange = false
                    it.filmScore.progressDrawable =
                        ContextCompat.getDrawable(context, R.drawable.ic_circular_bad_film_score)
                }
            }

            it.filmScore.progress = if (scoreInRange) {
                it.filmScoreValue.text = getString(R.string.film_score_percent, score)
                score
            } else {
                val zero = resources.getInteger(R.integer.zero_value)
                it.filmScoreValue.text = getString(R.string.film_score_percent, zero)
                zero
            }
        }

    private fun countOverviewLines() =
        with(bindingDisplay) {
            filmOverview.post {
                if (filmOverview.lineCount < resources.getInteger(R.integer.int_5)) setConditionBtnShow(
                    true
                )
                else setConditionBtnShow(false)
            }
        }

    private fun setConditionBtnShow(state: Boolean) =
        with(bindingDisplay.btnShowOverview) {
            if (state) layoutBtnShow.visibility = View.GONE
            else {
                layoutBtnShow.visibility = View.VISIBLE
                btnShowMore.setOnClickListener(onclick)
                btnShowLess.setOnClickListener(onclick)
            }
        }

    private fun setBtnShowVisibility(showMoreOverview: Boolean) =
        with(bindingDisplay) {
            btnShowOverview.let {
                if (showMoreOverview) {
                    it.btnShowMore.visibility = View.INVISIBLE
                    it.btnShowLess.visibility = View.VISIBLE
                    filmOverview.maxLines = Integer.MAX_VALUE
                    filmOverview.ellipsize = nullValue
                } else {
                    it.btnShowLess.visibility = View.INVISIBLE
                    it.btnShowMore.visibility = View.VISIBLE
                    filmOverview.maxLines = resources.getInteger(R.integer.int_5)
                    filmOverview.ellipsize = TextUtils.TruncateAt.END
                }
            }
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_show_more -> setBtnShowVisibility(true)
            R.id.btn_show_less -> setBtnShowVisibility(false)
            R.id.favorite_film_fab -> {
                statusFavorite = !statusFavorite
                setFavorite(statusFavorite)
            }
            R.id.film_score -> Toast.makeText(
                this,
                bindingDisplay.filmScoreValue.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setFavorite(stateFav: Boolean) {
        statusFavorite = if (stateFav) {
            favorite_film_fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
            true
        } else {
            favorite_film_fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_default
                )
            )
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}