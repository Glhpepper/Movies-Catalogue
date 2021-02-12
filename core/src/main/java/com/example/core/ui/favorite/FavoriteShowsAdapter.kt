package com.example.core.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.FavoriteShowsRowLayoutBinding
import com.example.core.di.FavoriteScope
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import javax.inject.Inject

@FavoriteScope
class FavoriteShowsAdapter @Inject constructor() :
    ListAdapter<FavoriteShowsDomain, FavoriteShowsAdapter.FavoriteViewHolder>(FavoriteShowsDiffUtils) {

    var onItemClick: ((FavoriteShowsDomain) -> Unit)? = null

    companion object FavoriteShowsDiffUtils : DiffUtil.ItemCallback<FavoriteShowsDomain>() {
        override fun areItemsTheSame(
            oldItem: FavoriteShowsDomain,
            newItem: FavoriteShowsDomain
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FavoriteShowsDomain,
            newItem: FavoriteShowsDomain
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = FavoriteShowsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteShows = getItem(position)
        holder.bind(favoriteShows)
    }

    fun getFavoriteAtPosition(position: Int): FavoriteShowsDomain {
        return getItem(position)
    }

    inner class FavoriteViewHolder(private val binding: FavoriteShowsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteShowsDomain: FavoriteShowsDomain) {
            binding.apply {
                favoriteShows = favoriteShowsDomain
                root.setOnClickListener {
                    onItemClick?.invoke(getItem(adapterPosition))
                }
                executePendingBindings()
            }
        }
    }
}