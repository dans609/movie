package com.danshouseproject.project.moviecatalogue.view.fragment.favfrag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danshouseproject.project.moviecatalogue.databinding.FragmentFavoriteFilmBinding
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnFavoriteItemClicked
import com.danshouseproject.project.moviecatalogue.view.activity.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.view.adapter.FavoriteFilmAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.AdditionalDataViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory

class FavoriteTvFragment : Fragment(), OnFavoriteItemClicked {

    private var _adapter: FavoriteFilmAdapter? = null
    private val adapter
        get() = _adapter

    private var _binding: FragmentFavoriteFilmBinding? = null
    private val binding
        get() = _binding

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
            ViewModelProvider(this, factory).get(AdditionalDataViewModel::class.java).let {
                it.getFavoriteFilm(false).observe(viewLifecycleOwner, { filmFavorite ->
                    if (!filmFavorite.isNullOrEmpty()) adapter?.setList(filmFavorite)
                    else displayImage(true)
                })
            }

            binding?.let {
                it.rvFavoriteFilm.setHasFixedSize(true)
                it.rvFavoriteFilm.layoutManager = LinearLayoutManager(activity)
                it.rvFavoriteFilm.adapter = adapter
            }
        }
    }

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

    override fun onFavoriteItemClick(data: FavoriteFilm) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }

    companion object {
        private const val FILM_FLAG = 101
    }
}