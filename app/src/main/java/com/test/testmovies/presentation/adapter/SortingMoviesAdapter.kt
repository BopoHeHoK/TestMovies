package com.test.testmovies.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testmovies.databinding.ItemSortingBinding
import com.test.testmovies.data.storage.model.sorting.Sorting

class SortingMoviesAdapter : RecyclerView.Adapter<SortingMoviesAdapter.SortingMoviesViewHolder>() {

    class SortingMoviesViewHolder(val binding: ItemSortingBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var sortingList = ArrayList<Sorting>()
    lateinit var onItemClick: ((Sorting) -> Unit)
    var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortingMoviesViewHolder {
        return SortingMoviesViewHolder(
            ItemSortingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SortingMoviesViewHolder, position: Int) {
        holder.binding.apply {
            tvSorting.text = sortingList[position].name
            root.setCardBackgroundColor(Color.WHITE)
            tvSorting.setTextColor(Color.BLACK)
            tvSorting.setOnClickListener {
                onItemClick.invoke(sortingList[position])
                selectedItem = position
            }
            if (selectedItem == position) {
                root.setCardBackgroundColor(Color.BLACK)
                tvSorting.setTextColor(Color.WHITE)
            }
        }

    }

    override fun getItemCount(): Int {
        return sortingList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSortingMoviesList(sortingList: List<Sorting>) {
        this.sortingList = sortingList as ArrayList<Sorting>
        notifyDataSetChanged()
    }

}