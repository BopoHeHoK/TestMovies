package com.test.testmovies.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.testmovies.databinding.ItemActorBinding
import com.test.testmovies.domain.model.movie_detail.Actor

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root)

    private var actorList = ArrayList<Actor>()
    private var emptyList = ArrayList<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ItemActorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        Glide.with(holder.itemView).load(actorList[position].image).into(holder.binding.imgActor)
        holder.binding.tvActorName.text = actorList[position].name
        holder.binding.tvActorAsCharacter.text = actorList[position].asCharacter
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setActorList(actorList: List<Actor>) {
        this.actorList = actorList as ArrayList<Actor>
        notifyDataSetChanged()
    }


}