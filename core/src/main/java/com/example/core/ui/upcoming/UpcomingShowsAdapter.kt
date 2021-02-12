package com.example.core.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ShowsRowLayoutBinding
import com.example.core.domain.model.shows.ShowsDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class UpcomingShowsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ShowsDomain, UpcomingShowsAdapter.UpcomingViewHolder>(UpcomingDiffUtils) {

    companion object UpcomingDiffUtils : DiffUtil.ItemCallback<ShowsDomain>() {
        override fun areItemsTheSame(oldItem: ShowsDomain, newItem: ShowsDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShowsDomain, newItem: ShowsDomain): Boolean {
            return oldItem.showsId == newItem.showsId
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingViewHolder {
        val view = ShowsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val shows = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(shows)
        }
        holder.bind(shows)
    }

    inner class UpcomingViewHolder(private val binding: ShowsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shows: ShowsDomain?) {
            binding.apply {
                show = shows
                executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (showsDomain: ShowsDomain) -> Unit) {
        fun onClick(showsDomain: ShowsDomain) = clickListener(showsDomain)
    }
}