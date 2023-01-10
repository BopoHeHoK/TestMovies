package com.test.testmovies.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.testmovies.R
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import com.test.testmovies.databinding.ItemMovieBinding

class FavoriteMoviesAdapter : RecyclerView.Adapter<FavoriteMoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    private var moviesList = ArrayList<FavoriteMovie>()
    lateinit var onItemFavoriteClick: ((FavoriteMovie) -> Unit)
    lateinit var onItemClick: ((FavoriteMovie) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(moviesList[position].image).into(holder.binding.imgMovie)
        holder.binding.apply {
            val raiting = moviesList[position].imDbRating
            tvRating.text = raiting
            if (raiting!!.isNotEmpty()) {
                if (raiting.toDouble() < 5.0) {
                    tvRating.setBackgroundColor(Color.RED)
                } else if (raiting.toDouble() < 7.0) {
                    tvRating.setBackgroundColor(Color.GRAY)
                }
            }
            tvName.text = moviesList[position].title
            btnAddToFavorites.setImageResource(R.drawable.ic_favorite_remove)
            btnAddToFavorites.setOnClickListener {
                onItemFavoriteClick.invoke(moviesList[position])
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesList(moviesList: List<FavoriteMovie>) {
        this.moviesList = moviesList as ArrayList<FavoriteMovie>
        notifyDataSetChanged()
    }
}