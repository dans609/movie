package com.danshouseproject.project.moviecatalogue.view.fragment

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
import com.danshouseproject.project.moviecatalogue.`object`.json.GetFilmId
import com.danshouseproject.project.moviecatalogue.databinding.FragmentListsFilmBinding
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback
import com.danshouseproject.project.moviecatalogue.view.activity.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.view.adapter.MoviesAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory
import com.danshouseproject.project.moviecatalogue.vo.Status

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

            for (idxId in id?.indices ?: return) {
                viewModel.getMovies(id[idxId]).observe(viewLifecycleOwner, { movies ->
                    if (movies != null)
                        when (movies.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                showProgressBar(false)
                                adapter.setList(movies.data ?: return@observe)
                                adapter.notifyDataSetChanged()
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(
                                    context,
                                    "There is something error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                })
            }

            binding?.let { bind ->
                bind.rvFilm.setHasFixedSize(true)
                bind.rvFilm.layoutManager = StaggeredGridLayoutManager(
                    resources.getInteger(R.integer.int_2),
                    StaggeredGridLayoutManager.VERTICAL
                )
                bind.rvFilm.adapter = adapter
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
        private const val FILM_FLAG = 100
        private val TAG = MoviesFragment::class.java.simpleName
    }
}