package com.danshouseproject.project.moviecatalogue.view.detail

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
import com.danshouseproject.project.moviecatalogue.utils.EspressoIdlingResource
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmGenre
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FilmInfo
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter.GenreAdapter
import com.danshouseproject.project.moviecatalogue.view.AdditionalDataViewModel
import com.danshouseproject.project.moviecatalogue.core.components.presentation.factory.ViewModelFactory
import com.danshouseproject.project.moviecatalogue.core.components.data.vo.Resource
import com.danshouseproject.project.moviecatalogue.core.components.data.vo.Status
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFilmActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var bindingDisplay: DisplayDetailUserInterfaceBinding
    private lateinit var additionalDataViewModel: AdditionalDataViewModel

    private var _activityDetailFilmBinding: ActivityDetailFilmBinding? = null
    private var statusFavorite: Boolean = false
    private var detailPageTitle: String? = null
    private var filmId: Int = 0
    private var getIntent: ListFilm? = null

    private val binding
        get() = _activityDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailFilmBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setContext(this)
        actionBarPreference { supportActionBar }
        showProgressBar(false)

        bindingDisplay = binding?.detailFilm as DisplayDetailUserInterfaceBinding
        detailPageTitle = getString(R.string.detail_film_page_title)

        genreAdapter = GenreAdapter()
        val filmFlag = intent.getIntExtra(EXTRA_FLAG, toInt(R.integer.zero_value))
        getIntent = intent.getParcelableExtra<ListFilm>(EXTRA_FILM) as ListFilm

        Glide.with(this)
            .asBitmap()
            .load(getIntent?.filmImage)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.splash_screen_image)
                    .override(toInt(R.integer.int_200), toInt(R.integer.int_300))
            )
            .into(bindingDisplay.filmImage)

        with(bindingDisplay) {
            getIntent?.let {
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
        checkFavorite()

        binding?.favoriteFilmFab?.setOnClickListener(this)
        bindingDisplay.filmScore.setOnClickListener(this)
    }

    private fun checkFavorite() {
        additionalDataViewModel.checkIsFavorite(filmId).observe(context, { count ->
            statusFavorite = if (count > resources.getInteger(R.integer.zero_value)) {
                favoriteDrawable(true)
                true
            } else {
                favoriteDrawable(false)
                false
            }
        })
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
        val genreViewModel =
            ViewModelProvider(this, factory).get(GenreViewModel::class.java)
        additionalDataViewModel =
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


    private fun showProgressBar(state: Boolean) =
        binding?.progressBar?.let {
            if (state) it.visibility = View.VISIBLE
            else it.visibility = View.GONE
        }


    private val LiveData<Resource<FilmGenre>>.observeGenres
        get() = this.observe(context, { listGenre ->
            if (listGenre != null) {
                when (listGenre.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        showProgressBar(false)
                        val listFilmGenre = ArrayList<String>()
                        for (index in listGenre.data?.genre?.indices ?: return@observe) {
                            if (listGenre.data.genre!![index].filmId == filmId) {
                                listFilmGenre.add(listGenre.data.genre?.get(index)?.genre.toString())
                                continue
                            }
                        }
                        genreAdapter.setListGenre(listFilmGenre)
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        getString(R.string.error_text).callToast
                    }
                }
            } else return@observe
        })

    private val LiveData<Resource<FilmInfo>>.observeFilmMoreInfo
        get() = this.observe(context, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        showProgressBar(false)
                        with(bindingDisplay) {
                            if (filmId == it.data?.filmId) {
                                filmCountryCode.text = it.data.isoCode
                                filmRating.text = it.data.filmRating
                            }
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        getString(R.string.error_text).callToast
                    }
                }
            } else return@observe
        })


    private val String.callToast
        get() = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()


    private fun progressBarAnimation(scores: Int) =
        bindingDisplay.let {
            EspressoIdlingResource.increment()
            lifecycleScope.launch(Dispatchers.Default) {
                var delay = toInt(R.integer.int_5)
                for (score in toInt(R.integer.zero_value)..scores) {
                    if (score % toInt(R.integer.optimal_max_thickness_ratio_size) == toInt(R.integer.zero_value)) delay -= toInt(
                        R.integer.int_1
                    )
                    if (delay == toInt(R.integer.zero_value)) delay = toInt(R.integer.int_1)
                    var breakTheLoops = false

                    delay(delay.toLong())
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
            EspressoIdlingResource.decrement()
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
            R.id.film_score -> bindingDisplay.filmScoreValue.text.toString().callToast
        }
    }

    private fun setFavorite(stateFav: Boolean) {
        if (stateFav) {
            additionalDataViewModel.addToFavorite(getIntent as ListFilm)
            getString(R.string.success_add_to_favorite).callToast
            favoriteDrawable(true)
        } else {
            additionalDataViewModel.removeFromFavorite(filmId)
            getString(R.string.remove_from_favorite).callToast
            favoriteDrawable(false)
        }
    }

    private fun favoriteDrawable(state: Boolean) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                if (state) binding?.favoriteFilmFab?.setImageDrawable(drawable { R.drawable.ic_favorite })
                else binding?.favoriteFilmFab?.setImageDrawable(drawable { R.drawable.ic_favorite_default })
            }
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
                Companion.context = it
                onclick = it
            }

        private fun toInt(id: Int) =
            context.resources.getInteger(id)

        private inline fun drawable(drawable: () -> Int): Drawable =
            ContextCompat.getDrawable(context.applicationContext, drawable()) as Drawable
    }
}