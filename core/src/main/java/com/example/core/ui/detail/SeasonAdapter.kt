package com.example.core.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.SeasonRowLayoutBinding
import com.example.core.domain.model.detail.shows.SeasonsItemDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SeasonAdapter @Inject constructor() :
    ListAdapter<SeasonsItemDomain, SeasonAdapter.SeasonViewHolder>(SeasonItemDiffUtils) {
    companion object SeasonItemDiffUtils : DiffUtil.ItemCallback<SeasonsItemDomain>() {
        override fun areItemsTheSame(
            oldItem: SeasonsItemDomain,
            newItem: SeasonsItemDomain
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SeasonsItemDomain,
            newItem: SeasonsItemDomain
        ): Boolean {
            return oldItem.posterPath == newItem.posterPath
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonViewHolder {
        val view =
            SeasonRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SeasonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = getItem(position)
        holder.bind(season)
    }

    class SeasonViewHolder(private val binding: SeasonRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(season: SeasonsItemDomain?) {
            binding.apply {
                seasonDetail = season
                executePendingBindings()
            }
        }
    }
}