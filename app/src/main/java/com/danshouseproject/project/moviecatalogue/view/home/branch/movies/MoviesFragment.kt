package com.danshouseproject.project.moviecatalogue.view.home.branch.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.utils.json.GetFilmId
import com.danshouseproject.project.moviecatalogue.databinding.FragmentListsFilmBinding
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback
import com.danshouseproject.project.moviecatalogue.view.detail.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter.MoviesAdapter
import com.danshouseproject.project.moviecatalogue.core.components.presentation.factory.ViewModelFactory
import com.danshouseproject.project.moviecatalogue.core.components.data.Resource

class MoviesFragment : Fragment(), OnItemClickCallback {

    private var _binding: FragmentListsFilmBinding? = null
    private val binding
        get() = _binding
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListsFilmBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            adapter = MoviesAdapter(this)
            showProgressBar(true)

            val id = GetFilmId(requireActivity()).fetchMoviesId()
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)

            for (idxId in id?.indices ?: return)
                viewModel.getMovies(id[idxId]).observeMovies

            binding?.let { bind ->
                val twoItems = resources.getInteger(R.integer.int_2)
                val layoutManager = StaggeredGridLayoutManager(twoItems, VERTICAL)

                bind.rvFilm.setHasFixedSize(true)
                bind.rvFilm.layoutManager = layoutManager
                bind.rvFilm.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        } else Log.d(TAG, getString(R.string.activity_null))
    }

    private val LiveData<Resource<ListFilm>>.observeMovies
        get() = this.observe(viewLifecycleOwner, { movies ->
            if (movies != null)
                when (movies) {
                    is Resource.Loading -> showProgressBar(true)
                    is Resource.Success -> {
                        showProgressBar(false)
                        adapter.setList(movies.data ?: return@observe)
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
        private const val FILM_FLAG = 100
        private val TAG = MoviesFragment::class.java.simpleName
    }
}