package com.danshouseproject.project.moviecatalogue.view.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.danshouseproject.project.moviecatalogue.databinding.DisplayDetailUserInterfaceBinding
import com.danshouseproject.project.moviecatalogue.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.adapter.GenreAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.AdditionalDataViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.GenreViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFilmActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var bindingDisplay: DisplayDetailUserInterfaceBinding

    private var _activityDetailFilmBinding: ActivityDetailFilmBinding? = null
    private var statusFavorite: Boolean = false
    private var detailPageTitle: String? = null
    private var filmId: Int = 0

    private val binding
        get() = _activityDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailFilmBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setContext(this)
        actionBarPreference { supportActionBar }

        bindingDisplay = binding?.detailFilm as DisplayDetailUserInterfaceBinding
        detailPageTitle = getString(R.string.detail_film_page_title)

        genreAdapter = GenreAdapter()
        val filmFlag = intent.getIntExtra(EXTRA_FLAG, toInt(R.integer.zero_value))
        val getIntent = intent.getParcelableExtra<ListFilm>(EXTRA_FILM) as ListFilm

        Glide.with(this)
            .asBitmap()
            .load(getIntent.filmImage)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.splash_screen_image)
                    .override(toInt(R.integer.int_200), toInt(R.integer.int_300))
            )
            .into(bindingDisplay.filmImage)

        with(bindingDisplay) {
            getIntent.let {
                filmId = it.filmId
                filmNameTitle.text = it.filmName
                filmOverview.text = it.filmOverview
                filmDuration.text = it.filmDuration
                filmReleaseDate.text = it.filmReleaseDate
                progressBarAnimation(it.filmScore)
            }
        }

        getViewModel(filmFlag)
        recyclerConfig()
        countOverviewLines()

        binding?.favoriteFilmFab?.setOnClickListener(this)
        bindingDisplay.filmScore.setOnClickListener(this)
    }

    private inline fun actionBarPreference(actionBar: () -> ActionBar?) =
        actionBar().let { actBar ->
            actBar?.setDisplayHomeAsUpEnabled(true)
            actBar?.title = detailPageTitle ?: getString(R.string.detail_film_page_title)
        }

    private fun recyclerConfig() =
        bindingDisplay.filmGenre.let { rv ->
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = genreAdapter
        }

    private fun getViewModel(flag: Int) {
        val factory = ViewModelFactory.getInstance(this)
        val genreViewModel = ViewModelProvider(this, factory).get(GenreViewModel::class.java)
        val additionalDataViewModel =
            ViewModelProvider(this, factory).get(AdditionalDataViewModel::class.java)

        when (flag) {
            toInt(R.integer.movies_flag) -> {
                genreViewModel.getMoviesGenres(filmId).observeGenres
                additionalDataViewModel.getMoviesAdditionalData(filmId).observeFilmMoreInfo
            }
            toInt(R.integer.tv_shows_flag) -> {
                genreViewModel.getTvShowsGenres(filmId).observeGenres
                additionalDataViewModel.getTvAdditionalData(filmId).observeFilmMoreInfo
            }
        }
    }

    private val LiveData<FilmGenre>.observeGenres
        get() = this.observe(context, { listGenre ->
            if (listGenre != null)
                genreAdapter.setListGenre(listGenre.genre)
            else return@observe
        })

    private val LiveData<Pair<String, String>>.observeFilmMoreInfo
        get() = this.observe(context, {
            if (it != null)
                with(bindingDisplay) {
                    filmCountryCode.text = it.first
                    filmRating.text = it.second
                }
            else return@observe
        })

    private fun progressBarAnimation(scores: Int) =
        bindingDisplay.let {
            lifecycleScope.launch(Dispatchers.Default) {
                for (score in toInt(R.integer.zero_value)..scores) {
                    var breakTheLoops = false
                    delay(toInt(R.integer.int_5).toLong())
                    withContext(Dispatchers.Main) {
                        it.filmScore.progressDrawable = when (score) {
                            in toInt(R.integer.score_value70)..toInt(R.integer.max_score_range) ->
                                drawable { R.drawable.ic_circular_progress_bar }
                            in toInt(R.integer.min_score_range_in_medium_rate)..toInt(R.integer.score_value69) ->
                                drawable { R.drawable.ic_circular_medium_film_score }
                            in toInt(R.integer.zero_value)..toInt(R.integer.max_score_range_in_bad_rate) ->
                                drawable { R.drawable.ic_circular_bad_film_score }
                            else -> {
                                breakTheLoops = true
                                drawable { R.drawable.ic_circular_bad_film_score }
                            }
                        }
                        setProgressScore(score)
                    }
                    if (breakTheLoops) break
                }
            }
        }

    private fun setProgressScore(score: Int) =
        bindingDisplay.let { bind ->
            score.also {
                bind.filmScoreValue.text =
                    if (it < zeroValue || it > maxScoreValue) {
                        bind.filmScore.progress = zeroValue
                        getString(R.string.film_score_percent, maxScoreValue)
                    } else {
                        bind.filmScore.progress = it
                        getString(R.string.film_score_percent, it)
                    }
            }
        }

    private fun countOverviewLines() =
        with(bindingDisplay) {
            filmOverview.post {
                if (filmOverview.lineCount < toInt(R.integer.int_5))
                    setConditionBtnShow(true)
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
                    filmOverview.maxLines = toInt(R.integer.int_5)
                    filmOverview.ellipsize = TextUtils.TruncateAt.END
                }
            }
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_show_more -> setBtnShowVisibility(true)
            R.id.btn_show_less -> setBtnShowVisibility(false)
            R.id.favorite_film_fab -> setFavorite(!statusFavorite)
            R.id.film_score -> Toast.makeText(
                this,
                bindingDisplay.filmScoreValue.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setFavorite(stateFav: Boolean) {
        statusFavorite = if (stateFav) {
            favorite_film_fab.setImageDrawable(drawable { R.drawable.ic_favorite })
            true
        } else {
            favorite_film_fab.setImageDrawable(drawable { R.drawable.ic_favorite_default })
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailFilmBinding = null
    }

    companion object {
        const val EXTRA_FILM = "extra_film"
        const val EXTRA_FLAG = "extra_flag"
        private lateinit var context: DetailFilmActivity
        private lateinit var onclick: View.OnClickListener
        private val nullValue = null

        private val maxScoreValue
            get() = context.resources.getInteger(R.integer.max_score_range)

        private val zeroValue
            get() = context.resources.getInteger(R.integer.zero_value)

        private fun setContext(context: DetailFilmActivity) =
            context.let {
                this.context = it
                this.onclick = it
            }

        private fun toInt(id: Int) =
            context.resources.getInteger(id)

        private inline fun drawable(drawable: () -> Int): Drawable =
            ContextCompat.getDrawable(context, drawable()) as Drawable
    }
}