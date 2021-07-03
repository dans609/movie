package com.danshouseproject.project.moviecatalogue.view.home.branch.tv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource
import com.danshouseproject.project.moviecatalogue.utils.json.GetFilmId
import com.danshouseproject.project.moviecatalogue.databinding.FragmentListsFilmBinding
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback
import com.danshouseproject.project.moviecatalogue.view.detail.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter.MoviesAdapter
import com.danshouseproject.project.moviecatalogue.core.components.presentation.factory.ViewModelFactory

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

            val id = GetFilmId(requireActivity()).fetchTvShowsId()
                ?: listOf(resources.getInteger(R.integer.max_score_range))
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory).get(TvShowsViewModel::class.java)

            for (idxId in id.indices) {
                viewModel.getTvShows(id[idxId]).observe(viewLifecycleOwner, { tv ->
                    if (tv != null)
                        when (tv) {
                            is Resource.Loading -> showProgressBar(true)
                            is Resource.Success -> {
                                showProgressBar(false)
                                adapter.setList(tv.data ?: return@observe)
                                adapter.notifyDataSetChanged()
                            }
                            is Resource.Error -> {
                                showProgressBar(false)
                                Toast.makeText(
                                    context,
                                    getString(R.string.error_text),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                })
            }

            binding?.let {
                val size = resources.getInteger(R.integer.int_2)
                val orientation = StaggeredGridLayoutManager.VERTICAL

                it.rvFilm.setHasFixedSize(true)
                it.rvFilm.layoutManager = StaggeredGridLayoutManager(size, orientation)
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