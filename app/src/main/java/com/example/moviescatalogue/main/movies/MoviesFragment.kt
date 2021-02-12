package com.example.moviescatalogue.main.movies

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.MoviesAdapter
import com.example.core.utils.Constants.Companion.API_KEY
import com.example.core.utils.Constants.Companion.PAGE
import com.example.core.utils.Constants.Companion.QUERY_API_KEY
import com.example.core.utils.Constants.Companion.QUERY_GENRE
import com.example.core.utils.Constants.Companion.QUERY_PAGE
import com.example.core.utils.Constants.Companion.QUERY_SEARCH
import com.example.core.utils.Constants.Companion.QUERY_SORT_BY
import com.example.core.utils.Constants.Companion.QUERY_YEAR_MOVIES
import com.example.core.utils.Constants.Companion.SHORT_BY
import com.example.moviescatalogue.MainActivity
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentMoviesBinding
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding

    private val moviesViewModel: MoviesViewModel by viewModels()
    private val queries: HashMap<String, String> = HashMap()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter(MoviesAdapter.OnClickListener {
            val action =
                MoviesFragmentDirections.actionMoviesFragmentToDetailMoviesActivity(it.moviesId)
            findNavController().navigate(action)
        })

        binding?.let {
            with(it.moviesRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }

        readFilter()
        setHasOptionsMenu(true)

        binding?.moviesFab?.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToMoviesBottomSheetFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuFavorite = menu.findItem(R.id.main_favorite)
        menuFavorite?.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.green))

        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun readFilter() {
        moviesViewModel.readGenreAndYear().observe(viewLifecycleOwner, { value ->
            queries[QUERY_API_KEY] = API_KEY
            queries[QUERY_SORT_BY] = SHORT_BY
            queries[QUERY_PAGE] = PAGE
            queries[QUERY_YEAR_MOVIES] = value.selectedYear
            queries[QUERY_GENRE] = value.selectedGenre
            getMovies()
        })
    }

    private fun getMovies() {
        moviesViewModel.getMovies(queries).observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val movieList = DataMapper.mapMoviesToMoviesDomain(movies.data)
                        moviesAdapter.submitList(movieList)
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        binding?.apply {
                            errorImgViewMovies.visibility = View.VISIBLE
                            errorTextViewMovies.visibility = View.VISIBLE
                            moviesRecyclerView.visibility = View.INVISIBLE
                            errorTextViewMovies.text = getString(R.string.something_wrong)
                        }
                    }
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            queries[QUERY_API_KEY] = API_KEY
            queries[QUERY_SEARCH] = query
            queries[QUERY_PAGE] = PAGE
            searchMovies()
        }
        closeKeyboard()
        return true
    }

    private fun searchMovies() {
        moviesViewModel.searchMovies(queries).observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val movieList = DataMapper.mapMoviesToMoviesDomain(movies.data)
                        moviesAdapter.submitList(movieList)
                        if (movies.data?.isEmpty() == true) showErrorOrNoData(
                            getString(R.string.no_data_movies)
                        ) else {
                            binding?.apply {
                                errorImgViewMovies.visibility = View.INVISIBLE
                                errorTextViewMovies.visibility = View.INVISIBLE
                                moviesRecyclerView.visibility = View.VISIBLE
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

    private fun showErrorOrNoData(string: String) {
        binding?.apply {
            errorImgViewMovies.visibility = View.VISIBLE
            errorTextViewMovies.visibility = View.VISIBLE
            moviesRecyclerView.visibility = View.INVISIBLE
            errorTextViewMovies.text = string
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.moviesRecyclerView?.showShimmer()
        } else {
            binding?.moviesRecyclerView?.hideShimmer()
        }
    }

    private fun closeKeyboard() {
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val hideOnEnter =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            hideOnEnter?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
        val windows = activity.window
        windows.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.moviesRecyclerView?.adapter = null
        _binding = null
    }
}