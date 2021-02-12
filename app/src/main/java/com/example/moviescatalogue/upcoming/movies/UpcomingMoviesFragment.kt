package com.example.moviescatalogue.upcoming.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.upcoming.UpcomingMoviesAdapter
import com.example.core.utils.Constants.Companion.API_KEY
import com.example.core.utils.Constants.Companion.PAGE
import com.example.core.utils.Constants.Companion.QUERY_API_KEY
import com.example.core.utils.Constants.Companion.QUERY_PAGE
import com.example.core.utils.Constants.Companion.QUERY_SORT_BY
import com.example.core.utils.Constants.Companion.QUERY_YEAR_UPCOMING_MOVIES
import com.example.core.utils.Constants.Companion.UPCOMING_SHORT_MOVIES
import com.example.core.utils.Constants.Companion.UPCOMING_YEAR
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentUpcomingMoviesBinding
import com.example.moviescatalogue.upcoming.UpcomingFragmentDirections
import com.example.moviescatalogue.upcoming.UpcomingViewModel
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment() {
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private val binding get() = _binding

    private val upcomingViewModel: UpcomingViewModel by viewModels()
    private val queries: HashMap<String, String> = HashMap()

    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingMoviesBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingMoviesAdapter = UpcomingMoviesAdapter(UpcomingMoviesAdapter.OnClickListener {
            val action =
                UpcomingFragmentDirections.actionUpcomingFragmentToDetailMoviesActivity(it.moviesId)
            findNavController().navigate(action)
        })

        binding?.let {
            with(it.upcomingMoviesRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = upcomingMoviesAdapter
            }
        }

        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_SORT_BY] = UPCOMING_SHORT_MOVIES
        queries[QUERY_PAGE] = PAGE
        queries[QUERY_YEAR_UPCOMING_MOVIES] = UPCOMING_YEAR
        upcomingViewModel.getUpcomingMovies(queries).observe(viewLifecycleOwner, { upcoming ->
            if (upcoming != null) {
                when (upcoming) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val movieList = DataMapper.mapMoviesToMoviesDomain(upcoming.data)
                        upcomingMoviesAdapter.submitList(movieList)
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        binding?.apply {
                            errorImgViewUpcomingMovies.visibility = View.VISIBLE
                            errorTextViewUpcomingMovies.visibility = View.VISIBLE
                            upcomingMoviesRecyclerView.visibility = View.INVISIBLE
                            errorTextViewUpcomingMovies.text = getString(R.string.something_wrong)
                        }
                    }
                }
            }
        })
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.upcomingMoviesRecyclerView?.showShimmer()
        } else {
            binding?.upcomingMoviesRecyclerView?.hideShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.upcomingMoviesRecyclerView?.adapter = null
        _binding = null
    }
}