package com.danshouseproject.project.moviecatalogue.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.DisplayFavoriteFilmBinding
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.view.OnFavoriteItemClicked

class FavoriteFilmAdapter :
    PagedListAdapter<FavoriteFilm, FavoriteFilmAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    private lateinit var onFavoriteItemClicked: OnFavoriteItemClicked

    inner class FavoriteViewHolder(private val binding: DisplayFavoriteFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteFilm) {
            with(itemView) {
                context.resources.let { res ->
                    Glide.with(context)
                        .asBitmap()
                        .load(data.posterPath)
                        .apply(
                            RequestOptions.placeholderOf(R.color.colorAccent)
                                .error(R.drawable.ic_fail_to_load)
                                .override(
                                    res.getInteger(R.integer.score_value70),
                                    res.getInteger(R.integer.max_score_range)
                                )
                        )
                        .into(binding.posterFilm)

                    binding.filmName.text = data.filmName
                    binding.tvDescription.text = data.overview
                    binding.filmRating.text =
                        context.getString(R.string.film_rating_responsive, data.filmScore)

                    binding.btnFavorite.onClicked(context, data)
                    binding.btnDetail.onClicked(context, data)
                }
            }
        }
    }

    private fun View.onClicked(context: Context, data: FavoriteFilm) {
        this.setOnClickListener {
            onFavoriteItemClicked.onFavoriteItemClick(data, this.id)
            data.filmName.callToast(context)
        }
    }

    private fun <T> T.callToast(context: Context) {
        Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setFavoriteItemClickCallback(onFavoriteItemClicked: OnFavoriteItemClicked) =
        onFavoriteItemClicked.let { this.onFavoriteItemClicked = it }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            DisplayFavoriteFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val filmFavorite = getItem(position)
        if (filmFavorite != null) holder.bind(filmFavorite)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteFilm>() {
            override fun areItemsTheSame(oldItem: FavoriteFilm, newItem: FavoriteFilm): Boolean =
                oldItem.filmId == newItem.filmId

            override fun areContentsTheSame(oldItem: FavoriteFilm, newItem: FavoriteFilm): Boolean =
                oldItem == newItem
        }
    }
}