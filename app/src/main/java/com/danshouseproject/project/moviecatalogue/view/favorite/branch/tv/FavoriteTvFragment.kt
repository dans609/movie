package com.danshouseproject.project.moviecatalogue.view.favorite.branch.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.FragmentFavoriteFilmBinding
import com.danshouseproject.project.moviecatalogue.core.utils.SortUtils
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnFavoriteItemClicked
import com.danshouseproject.project.moviecatalogue.view.detail.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter.FavoriteFilmAdapter
import com.danshouseproject.project.moviecatalogue.view.AdditionalDataViewModel
import com.danshouseproject.project.moviecatalogue.core.components.presentation.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteTvFragment : Fragment(), OnFavoriteItemClicked {

    private var _adapter: FavoriteFilmAdapter? = null
    private val adapter
        get() = _adapter

    private var _binding: FragmentFavoriteFilmBinding? = null
    private val binding
        get() = _binding

    private var _viewModel: AdditionalDataViewModel? = null
    private val viewModel
    get() = _viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteFilmBinding.inflate(inflater, container, false)
        _adapter = FavoriteFilmAdapter()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter?.setFavoriteItemClickCallback(this)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            _viewModel = ViewModelProvider(this, factory).get(AdditionalDataViewModel::class.java)
            viewModel?.getFavoriteFilm(false)?.observeFavorite
            viewModel?.getOrderedFilm(NEWEST)?.observeFavorite

            binding?.let {
                it.rvFavoriteFilm.setHasFixedSize(true)
                it.rvFavoriteFilm.layoutManager = LinearLayoutManager(activity)
                it.rvFavoriteFilm.adapter = adapter
            }
        }
    }

    private val LiveData<PagedList<FavoriteFilm>>.observeFavorite
        get() = this.observe(viewLifecycleOwner, { filmFavorite ->
            if (!filmFavorite.isNullOrEmpty()) adapter?.submitList(filmFavorite)
            else displayImage(true)
        })

    private fun displayImage(state: Boolean) {
        binding?.let {
            if (state) {
                it.dataNotFoundLayout.noDataViewGroup.visibility = View.VISIBLE
                binding?.rvFavoriteFilm?.visibility = View.GONE
            }
            else {
                it.dataNotFoundLayout.noDataViewGroup.visibility = View.GONE
                binding?.rvFavoriteFilm?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onFavoriteItemClick(data: FavoriteFilm, viewId: Int) {
        when (viewId) {
            R.id.btn_favorite -> {
                val snackbarText = getString(R.string.delete_text_statement)
                val action = getString(R.string.accept_text)
                val duration = Snackbar.LENGTH_LONG
                val view = binding?.root as View
                Snackbar.make(view, snackbarText, duration)
                    .setAction(action) { viewModel?.removeFromFavorite(data.filmId) }
                    .setBackgroundTint(getColor(R.color.colorYellow))
                    .setActionTextColor(getColor(R.color.colorRed))
                    .setTextColor(getColor(R.color.colorAestheticBlack))
                    .show()
            }
            R.id.btn_detail -> {
                val castInstance = ListFilm(
                    filmId = data.filmId,
                    filmName = data.filmName,
                    filmImage = data.posterPath,
                    filmOverview = data.overview,
                    filmScore = data.filmScore,
                    filmReleaseDate = data.releaseDate,
                    filmDuration = data.duration,
                    isMovies = data.isMovies
                )
                Intent(activity, DetailFilmActivity::class.java).let { intent ->
                    intent.putExtra(DetailFilmActivity.EXTRA_FILM, castInstance)
                    intent.putExtra(DetailFilmActivity.EXTRA_FLAG, FILM_FLAG)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getColor(colorId: Int) =
        ContextCompat.getColor(requireActivity(), colorId)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _viewModel = null
    }

    companion object {
        private const val FILM_FLAG = 101
        private const val NEWEST = SortUtils.SORT_ID_DES
    }
}