package com.example.core.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ProductionRowLayoutBinding
import com.example.core.domain.model.detail.movies.ProductionCompanyDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ProductionAdapter @Inject constructor() :
    ListAdapter<ProductionCompanyDomain, ProductionAdapter.ProductionViewHolder>(ProductionDiffUtils) {
    companion object ProductionDiffUtils : DiffUtil.ItemCallback<ProductionCompanyDomain>() {
        override fun areItemsTheSame(
            oldItem: ProductionCompanyDomain,
            newItem: ProductionCompanyDomain
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductionCompanyDomain,
            newItem: ProductionCompanyDomain
        ): Boolean {
            return oldItem.logoPath == newItem.logoPath
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductionViewHolder {
        val view =
            ProductionRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductionViewHolder, position: Int) {
        val production = getItem(position)
        holder.bind(production)
    }

    class ProductionViewHolder(private val binding: ProductionRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productionCompany: ProductionCompanyDomain?) {
            binding.apply {
                production = productionCompany
                executePendingBindings()
            }
        }
    }
}