package com.example.moviescatalogue.main.shows

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
import com.example.core.ui.ShowsAdapter
import com.example.core.utils.Constants.Companion.API_KEY
import com.example.core.utils.Constants.Companion.PAGE
import com.example.core.utils.Constants.Companion.QUERY_API_KEY
import com.example.core.utils.Constants.Companion.QUERY_GENRE
import com.example.core.utils.Constants.Companion.QUERY_PAGE
import com.example.core.utils.Constants.Companion.QUERY_SEARCH
import com.example.core.utils.Constants.Companion.QUERY_SORT_BY
import com.example.core.utils.Constants.Companion.QUERY_YEAR_SHOWS
import com.example.core.utils.Constants.Companion.SHORT_BY
import com.example.moviescatalogue.MainActivity
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentShowsBinding
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding

    private val showsViewModel: ShowsViewModel by viewModels()
    private val queries: HashMap<String, String> = HashMap()

    private lateinit var showsAdapter: ShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowsBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showsAdapter = ShowsAdapter(ShowsAdapter.OnClickListener {
            val action =
                ShowsFragmentDirections.actionShowsFragmentToDetailShowsActivity(it.showsId)
            findNavController().navigate(action)
        })

        binding?.let {
            with(it.showsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = showsAdapter
            }
        }

        readFilter()
        setHasOptionsMenu(true)

        binding?.showsFab?.setOnClickListener {
            val action = ShowsFragmentDirections.actionShowsFragmentToShowsBottomSheetFragment()
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
        showsViewModel.readGenreAndYear().observe(viewLifecycleOwner, { value ->
            queries[QUERY_API_KEY] = API_KEY
            queries[QUERY_SORT_BY] = SHORT_BY
            queries[QUERY_PAGE] = PAGE
            queries[QUERY_YEAR_SHOWS] = value.selectedYear
            queries[QUERY_GENRE] = value.selectedGenre
            getShows()
        })
    }

    private fun getShows() {
        showsViewModel.getShows(queries).observe(viewLifecycleOwner, { shows ->
            if (shows != null) {
                when (shows) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val showsList = DataMapper.mapShowsToShowsDomain(shows.data)
                        showsAdapter.submitList(showsList)
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        binding?.apply {
                            errorImgViewShows.visibility = View.VISIBLE
                            errorTextViewShows.visibility = View.VISIBLE
                            showsRecyclerView.visibility = View.INVISIBLE
                            errorTextViewShows.text = getString(R.string.something_wrong)
                        }
                    }
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            queries[QUERY_API_KEY] = API_KEY
            queries[QUERY_PAGE] = PAGE
            queries[QUERY_SEARCH] = query
            searchShows()
        }
        closeKeyboard()
        return true
    }

    private fun searchShows() {
        showsViewModel.searchShows(queries).observe(viewLifecycleOwner, { shows ->
            if (shows != null) {
                when (shows) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val showsList = DataMapper.mapShowsToShowsDomain(shows.data)
                        showsAdapter.submitList(showsList)
                        if (shows.data?.isEmpty() == true) showErrorOrNoData(
                            getString(R.string.no_data_shows)
                        ) else {
                            binding?.apply {
                                errorImgViewShows.visibility = View.INVISIBLE
                                errorTextViewShows.visibility = View.INVISIBLE
                                showsRecyclerView.visibility = View.VISIBLE
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

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun showErrorOrNoData(string: String) {
        binding?.apply {
            errorImgViewShows.visibility = View.VISIBLE
            errorTextViewShows.visibility = View.VISIBLE
            showsRecyclerView.visibility = View.INVISIBLE
            errorTextViewShows.text = string
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.showsRecyclerView?.showShimmer()
        } else {
            binding?.showsRecyclerView?.hideShimmer()
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
        binding?.showsRecyclerView?.adapter = null
        _binding = null
    }
}