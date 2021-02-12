package com.example.moviescatalogue.favorite.movies

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
import com.example.core.ui.favorite.FavoriteMoviesAdapter
import com.example.core.utils.Constants.Companion.DEEP_LINK_MOVIES
import com.example.core.utils.SwipeToDelete
import com.example.moviescatalogue.R
import com.example.moviescatalogue.favorite.FavoriteActivity
import com.example.moviescatalogue.favorite.FavoriteFragment
import com.example.moviescatalogue.favorite.FavoriteViewModel
import com.example.moviescatalogue.favorite.databinding.FragmentFavoriteMoviesBinding
import com.example.moviescatalogue.favorite.utils.ViewModelFactory
import com.example.moviescatalogue.utils.DataMapper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoriteMoviesFragment : Fragment() {
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as FavoriteActivity).favoriteComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            with(it.favoriteMoviesRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteMoviesAdapter
            }
        }

        getFavoriteMovies()
        swipeDeleteMovies()

        favoriteMoviesAdapter.onItemClick = {
            val action = Uri.parse(DEEP_LINK_MOVIES + it.id)
            findNavController().navigate(action)
        }
    }

    private fun getFavoriteMovies() {
        favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favorite ->
            if (favorite != null) {
                when (favorite) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val favoriteMovies =
                            DataMapper.mapFavoriteMoviesToFavoriteMoviesDomain(favorite.data)
                        favoriteMoviesAdapter.submitList(favoriteMovies)
                        if (favorite.data?.isEmpty() == true) showErrorOrNoData(
                            getString(R.string.no_data_favorite)
                        ) else {
                            binding?.apply {
                                noDataImgFavoriteMovies.visibility = View.INVISIBLE
                                noDataTextFavoriteMovies.visibility = View.INVISIBLE
                                favoriteMoviesRecyclerView.visibility = View.VISIBLE
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

    private fun swipeDeleteMovies() {
        val swipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoritePosition = favoriteMoviesAdapter.getFavoriteAtPosition(position)
                favoriteViewModel.deleteFavoriteMovies(favoritePosition)
                Snackbar.make(view as View, R.string.state_false, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo) {
                        favoriteViewModel.insertFavoriteMovies(favoritePosition)
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding?.favoriteMoviesRecyclerView)
    }

    private fun showErrorOrNoData(string: String) {
        binding?.apply {
            noDataImgFavoriteMovies.visibility = View.VISIBLE
            noDataTextFavoriteMovies.visibility = View.VISIBLE
            favoriteMoviesRecyclerView.visibility = View.INVISIBLE
            noDataTextFavoriteMovies.text = string
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.favoriteMoviesRecyclerView?.showShimmer()
        } else {
            binding?.favoriteMoviesRecyclerView?.hideShimmer()
        }
    }

    override fun onResume() {
        super.onResume()
        val parentFragment = this.parentFragment as FavoriteFragment?
        if (parentFragment?.emptySearchTextMovies == true) {
            getFavoriteMovies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.favoriteMoviesRecyclerView?.adapter = null
        _binding = null
    }
}