package com.test.testmovies.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.testmovies.R
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.databinding.ItemMovieBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    private var moviesList = ArrayList<Movie>()
    lateinit var onItemClick: ((Movie) -> Unit)
    lateinit var onItemFavoriteClick: ((Movie) -> Unit)
    private var favoriteItemList = ArrayList<Int>()

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
            if (raiting != null) {
                if (raiting.isBlank()) {
                    tvRating.text = " - "
                    tvRating.setBackgroundColor(Color.GRAY)
                } else {
                    tvRating.text = raiting
                }
            }
            if (raiting!!.isNotEmpty()) {
                if (raiting.toDouble() < 5.0) {
                    tvRating.setBackgroundColor(Color.RED)
                } else if (raiting.toDouble() < 7.0) {
                    tvRating.setBackgroundColor(Color.GRAY)
                } else {
                    tvRating.setBackgroundResource(R.color.green)
                }
            }
            tvName.text = moviesList[position].title
            btnAddToFavorites.setImageResource(R.drawable.ic_favorite_add)
            favoriteItemList.forEach { favoriteItem ->
                if (favoriteItem == position) {
                    btnAddToFavorites.setImageResource(R.drawable.ic_favorite_remove)
                }
            }
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
    fun setMoviesList(moviesList: List<Movie>, favoriteItemList: List<Int>) {
        this.moviesList = moviesList as ArrayList<Movie>
        this.favoriteItemList = favoriteItemList as ArrayList<Int>
        notifyDataSetChanged()
    }
}