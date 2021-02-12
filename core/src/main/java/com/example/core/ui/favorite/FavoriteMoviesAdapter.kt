package com.example.core.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.FavoriteMoviesRowLayoutBinding
import com.example.core.di.FavoriteScope
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import javax.inject.Inject

@FavoriteScope
class FavoriteMoviesAdapter @Inject constructor() :
    ListAdapter<FavoriteMoviesDomain, FavoriteMoviesAdapter.FavoriteViewHolder>(
        FavoriteMoviesDiffUtils
    ) {

    var onItemClick: ((FavoriteMoviesDomain) -> Unit)? = null

    companion object FavoriteMoviesDiffUtils : DiffUtil.ItemCallback<FavoriteMoviesDomain>() {
        override fun areItemsTheSame(
            oldItem: FavoriteMoviesDomain,
            newItem: FavoriteMoviesDomain
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FavoriteMoviesDomain,
            newItem: FavoriteMoviesDomain
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = FavoriteMoviesRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteMovies = getItem(position)
        holder.bind(favoriteMovies)
    }

    fun getFavoriteAtPosition(position: Int): FavoriteMoviesDomain {
        return getItem(position)
    }

    inner class FavoriteViewHolder(private val binding: FavoriteMoviesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteMoviesDomain: FavoriteMoviesDomain?) {
            binding.apply {
                favoriteMovies = favoriteMoviesDomain
                root.setOnClickListener {
                    onItemClick?.invoke(getItem(adapterPosition))
                }
                executePendingBindings()
            }
        }
    }
}