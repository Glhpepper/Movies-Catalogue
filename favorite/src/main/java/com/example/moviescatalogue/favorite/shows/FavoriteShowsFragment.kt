package com.example.moviescatalogue.favorite.shows

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Resource
import com.example.core.ui.favorite.FavoriteShowsAdapter
import com.example.core.utils.Constants.Companion.DEEP_LINK_SHOWS
import com.example.core.utils.SwipeToDelete
import com.example.moviescatalogue.R
import com.example.moviescatalogue.favorite.FavoriteActivity
import com.example.moviescatalogue.favorite.FavoriteFragment
import com.example.moviescatalogue.favorite.FavoriteViewModel
import com.example.moviescatalogue.favorite.databinding.FragmentFavoriteShowsBinding
import com.example.moviescatalogue.favorite.utils.ViewModelFactory
import com.example.moviescatalogue.utils.DataMapper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoriteShowsFragment : Fragment() {
    private var _binding: FragmentFavoriteShowsBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var favoriteShowsAdapter: FavoriteShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteShowsBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as FavoriteActivity).favoriteComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            with(it.favoriteShowsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteShowsAdapter
            }
        }

        getFavoriteShows()
        swipeDeleteShows()

        favoriteShowsAdapter.onItemClick = {
            val action = Uri.parse(DEEP_LINK_SHOWS + it.id)
            findNavController().navigate(action)
        }
    }

    private fun getFavoriteShows() {
        favoriteViewModel.getFavoriteShows().observe(viewLifecycleOwner, { favorite ->
            if (favorite != null) {
                when (favorite) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val favoriteShows =
                            DataMapper.mapFavoriteShowsToFavoriteShowsDomain(favorite.data)
                        favoriteShowsAdapter.submitList(favoriteShows)
                        if (favorite.data?.isEmpty() == true) showErrorOrNoData(
                            getString(R.string.no_data_favorite)
                        ) else {
                            binding?.apply {
                                noDataTextFavoriteShows.visibility = View.INVISIBLE
                                noDataImgFavoriteShows.visibility = View.INVISIBLE
                                favoriteShowsRecyclerView.visibility = View.VISIBLE
                            }
                        }
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        showErrorOrNoData(getString(R.string.something_wrong))
                    }
                }
            }
        })
    }

    private fun swipeDeleteShows() {
        val swipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoritePosition = favoriteShowsAdapter.getFavoriteAtPosition(position)
                favoriteViewModel.deleteFavoriteShows(favoritePosition)
                Snackbar.make(view as View, R.string.state_false, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo) {
                        favoriteViewModel.insertFavoriteShows(favoritePosition)
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding?.favoriteShowsRecyclerView)
    }

    private fun showErrorOrNoData(string: String) {
        binding?.apply {
            noDataImgFavoriteShows.visibility = View.VISIBLE
            noDataTextFavoriteShows.visibility = View.VISIBLE
            favoriteShowsRecyclerView.visibility = View.INVISIBLE
            noDataTextFavoriteShows.text = string
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.favoriteShowsRecyclerView?.showShimmer()
        } else {
            binding?.favoriteShowsRecyclerView?.hideShimmer()
        }
    }

    override fun onResume() {
        super.onResume()
        val parentFragment = this.parentFragment as FavoriteFragment?
        if (parentFragment?.emptySearchTextShows == true) {
            getFavoriteShows()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.favoriteShowsRecyclerView?.adapter = null
        _binding = null
    }
}