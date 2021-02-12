package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.MoviesRowLayoutBinding
import com.example.core.domain.model.movies.MoviesDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class MoviesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MoviesDomain, MoviesAdapter.MoviesViewHolder>(MoviesDiffUtils) {

    companion object MoviesDiffUtils : DiffUtil.ItemCallback<MoviesDomain>() {
        override fun areItemsTheSame(oldItem: MoviesDomain, newItem: MoviesDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviesDomain, newItem: MoviesDomain): Boolean {
            return oldItem.moviesId == newItem.moviesId
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val view =
            MoviesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movies)
        }
        holder.bind(movies)
    }

    inner class MoviesViewHolder(private val binding: MoviesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: MoviesDomain?) {
            binding.apply {
                movie = movies
                executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (moviesDomain: MoviesDomain) -> Unit) {
        fun onClick(moviesDomain: MoviesDomain) = clickListener(moviesDomain)
    }
}