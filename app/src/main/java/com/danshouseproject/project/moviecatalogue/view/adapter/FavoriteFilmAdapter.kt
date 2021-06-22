package com.danshouseproject.project.moviecatalogue.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.DisplayFavoriteFilmBinding
import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm
import com.danshouseproject.project.moviecatalogue.view.OnFavoriteItemClicked
import com.danshouseproject.project.moviecatalogue.view.OnItemClickCallback

class FavoriteFilmAdapter : RecyclerView.Adapter<FavoriteFilmAdapter.FavoriteViewHolder>() {
    private lateinit var onFavoriteItemClicked: OnFavoriteItemClicked
    private val listFavoriteFilm = ArrayList<FavoriteFilm>()

    fun setList(listFilm: List<FavoriteFilm>) {
        listFavoriteFilm.clear()
        listFavoriteFilm.addAll(listFilm)
        notifyDataSetChanged()
    }

    fun setFavoriteItemClickCallback(onFavoriteItemClicked: OnFavoriteItemClicked) {
        this.onFavoriteItemClicked = onFavoriteItemClicked
    }

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

                    setOnClickListener { onFavoriteItemClicked.onFavoriteItemClick(data) }
                    binding.btnDetail.setOnClickListener {
                        Toast.makeText(context, data.filmName, Toast.LENGTH_SHORT).show()
                        onFavoriteItemClicked.onFavoriteItemClick(data)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            DisplayFavoriteFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(listFavoriteFilm[position])

    override fun getItemCount(): Int = listFavoriteFilm.size

}