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
import com.danshouseproject.project.moviecatalogue.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback
import com.danshouseproject.project.moviecatalogue.view.activity.DetailFilmActivity
import com.danshouseproject.project.moviecatalogue.view.adapter.MoviesAdapter
import com.danshouseproject.project.moviecatalogue.viewmodel.MoviesViewModel
import com.danshouseproject.project.moviecatalogue.viewmodel.factory.ViewModelFactory

class MoviesFragment : Fragment(), OnItemClickCallback {

    private lateinit var binding: FragmentListsFilmBinding
    private var list: ArrayList<ListFilm> = arrayListOf()

    companion object {
        private const val FILM_FLAG = 100
        private lateinit var onItemClicked: OnItemClickCallback
        private val TAG = MoviesFragment::class.java.simpleName

        private fun setOnItemClicked(onClicked: OnItemClickCallback) {
            onItemClicked = onClicked
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsFilmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnItemClicked(this)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)
            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
                list.addAll(movies)
            })

            with(binding) {
                rvFilm.setHasFixedSize(true)
                rvFilm.layoutManager = StaggeredGridLayoutManager(resources.getInteger(R.integer.int_2), StaggeredGridLayoutManager.VERTICAL)
                val adapter = MoviesAdapter(list, onItemClicked)
                rvFilm.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        } else Log.d(TAG, getString(R.string.activity_null))
    }

    override fun onItemClicked(data: ListFilm, position: Int) =
        Intent(activity, DetailFilmActivity::class.java).let { intent ->
            intent.putExtra(DetailFilmActivity.EXTRA_FILM, data)
            intent.putExtra(DetailFilmActivity.EXTRA_FLAG, FILM_FLAG)
            intent.putExtra(DetailFilmActivity.EXTRA_MOVIES_POSITION, position)
            startActivity(intent)
        }

}