package com.danshouseproject.project.moviecatalogue.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.FragmentListsFilmBinding
import com.danshouseproject.project.moviecatalogue.`object`.json.GetFilmId
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback
import com.danshouseproject.project.moviecatalogue.view.activity.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.view.adapter.MoviesAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.TvShowsViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory

class TvShowsFragment : Fragment(), OnItemClickCallback {

    private var _binding: FragmentListsFilmBinding? = null
    private val binding
        get() = _binding
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListsFilmBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            adapter = MoviesAdapter(this)
            showProgressBar(true)

            val id = GetFilmId(requireActivity()).fetchTvShowsId() ?: listOf(resources.getInteger(R.integer.max_score_range))
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory).get(TvShowsViewModel::class.java)

            for (idxId in id.indices) {
                viewModel.getTvShows(id[idxId]).observe(viewLifecycleOwner, { tv ->
                    adapter.setList(tv)
                    showProgressBar(false)
                })
            }

            binding?.let {
                it.rvFilm.setHasFixedSize(true)
                it.rvFilm.layoutManager = StaggeredGridLayoutManager(resources.getInteger(R.integer.int_2), StaggeredGridLayoutManager.VERTICAL)
                it.rvFilm.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        } else Log.d(TAG, getString(R.string.activity_null))
    }

    override fun onItemClicked(data: ListFilm, position: Int) =
        Intent(activity, DetailFilmActivity::class.java).let { intent ->
            intent.putExtra(DetailFilmActivity.EXTRA_FILM, data)
            intent.putExtra(DetailFilmActivity.EXTRA_FLAG, FILM_FLAG)
            startActivity(intent)
        }

    private fun showProgressBar(state: Boolean) {
        binding?.progressBar?.visibility =
            if (state) View.VISIBLE
            else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FILM_FLAG = 101
        private val TAG = TvShowsFragment::class.java.simpleName
    }
}