package com.danshouseproject.project.moviecatalogue.core.components.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.danshouseproject.project.moviecatalogue.databinding.DisplayFilmGenreBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val listGenre = ArrayList<String>()

    fun setListGenre(listGenreInt: List<String>?) =
        listGenreInt.let {
            if (it == null) return

            listGenre.clear()
            listGenre.addAll(it)
            notifyDataSetChanged()
        }

    inner class GenreViewHolder(private val binding: DisplayFilmGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) {
            with(itemView) {
                context.let { ctx ->
                    binding.genre.text = genre
                    this.setOnClickListener {
                        Toast.makeText(
                            ctx,
                            binding.genre.text.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            DisplayFilmGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.bind(listGenre[position])

    override fun getItemCount(): Int = listGenre.size
}