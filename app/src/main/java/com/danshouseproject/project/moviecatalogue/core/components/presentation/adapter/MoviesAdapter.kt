package com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.DisplayListFilmBinding
import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback

class MoviesAdapter(private val listenerClick: OnItemClickCallback) : RecyclerView.Adapter<MoviesAdapter.FilmViewHolder>() {
    private val listFilm = ArrayList<ListFilm>()

    fun setList(data: ListFilm) {
        listFilm.add(data)
        notifyDataSetChanged()
    }

    inner class FilmViewHolder(private val binding: DisplayListFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListFilm, position: Int) {
            binding.apply {
                with(itemView) {
                    Glide.with(context)
                        .asBitmap()
                        .load(data.filmImage)
                        .apply(
                            RequestOptions.placeholderOf(R.color.colorAccent)
                                .error(R.drawable.ic_fail_to_load)
                                .override(context.resources.getInteger(R.integer.max_score_range), context.resources.getInteger(R.integer.int_200))
                        )
                        .into(posterFilm)

                    filmName.text = data.filmName
                    filmOverview.text = data.filmOverview

                    setOnClickListener {
                        Toast.makeText(
                            context,
                            filmName.text.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        listenerClick.onItemClicked(data, position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        FilmViewHolder(
            DisplayListFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) =
        holder.bind(listFilm[position], position)

    override fun getItemCount(): Int =
        listFilm.size
}