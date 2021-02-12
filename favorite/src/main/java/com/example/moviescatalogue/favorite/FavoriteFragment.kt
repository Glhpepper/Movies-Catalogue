package com.example.moviescatalogue.favorite

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.ui.detail.ViewPagerAdapter
import com.example.core.ui.favorite.FavoriteMoviesAdapter
import com.example.core.ui.favorite.FavoriteShowsAdapter
import com.example.core.utils.Constants.Companion.FIRST_ITEM_VIEWPAGER
import com.example.moviescatalogue.R
import com.example.moviescatalogue.favorite.databinding.FragmentFavoriteBinding
import com.example.moviescatalogue.favorite.movies.FavoriteMoviesFragment
import com.example.moviescatalogue.favorite.shows.FavoriteShowsFragment
import com.example.moviescatalogue.favorite.utils.ViewModelFactory
import com.example.moviescatalogue.utils.DataMapper
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class FavoriteFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    @Inject
    lateinit var favoriteShowsAdapter: FavoriteShowsAdapter

    private var _emptySearchTextMovies = false
    val emptySearchTextMovies
        get() = _emptySearchTextMovies

    private var _emptySearchTextShows = false
    val emptySearchTextShows
        get() = _emptySearchTextShows

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as FavoriteActivity).favoriteComponent.inject(this)
    }

    private fun setupView() {
        val fragment = ArrayList<Fragment>()
        fragment.add(FavoriteMoviesFragment())
        fragment.add(FavoriteShowsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.movies_fragment))
        titles.add(getString(R.string.shows_fragment))

        val pagerAdapter = ViewPagerAdapter(null, fragment, childFragmentManager, lifecycle)

        binding?.viewPagerFavorite?.adapter = pagerAdapter

        binding?.tabFavoriteMovies?.let { tabLayout ->
            binding?.viewPagerFavorite?.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = titles[position]
                }.attach()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contextual_menu, menu)

        val search = menu.findItem(R.id.search_favorite)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        closeKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val currentItem = binding?.viewPagerFavorite?.currentItem
        if (newText != null) {
            if (currentItem == FIRST_ITEM_VIEWPAGER) {
                _emptySearchTextMovies = false
                _emptySearchTextShows = true
                favoriteViewModel.searchFavoriteMovies(newText)
                    .observe(viewLifecycleOwner, { favorite ->
                        if (favorite != null) {
                            val favoriteMovies =
                                DataMapper.mapFavoriteMoviesToFavoriteMoviesDomain(favorite)
                            favoriteMoviesAdapter.submitList(favoriteMovies)
                        }
                    })
            } else {
                _emptySearchTextMovies = true
                _emptySearchTextShows = false
                favoriteViewModel.searchFavoriteShows(newText)
                    .observe(viewLifecycleOwner, { favorite ->
                        if (favorite != null) {
                            val favoriteShows =
                                DataMapper.mapFavoriteShowsToFavoriteShowsDomain(favorite)
                            favoriteShowsAdapter.submitList(favoriteShows)
                        }
                    })
            }
        }
        return true
    }

    private fun closeKeyboard() {
        val activity = activity as FavoriteActivity

        val view = activity.currentFocus
        if (view != null) {
            val hideOnEnter =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            hideOnEnter?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
        val windows = activity.window
        windows.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_favorite -> showDialogOption()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogOption() {
        val currentItem = binding?.viewPagerFavorite?.currentItem
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(
            if (currentItem == FIRST_ITEM_VIEWPAGER) getString(R.string.delete_all_movies)
            else getString(R.string.delete_all_shows)
        )
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (currentItem == FIRST_ITEM_VIEWPAGER) {
                    favoriteViewModel.deleteAllFavoriteMovies()
                } else {
                    favoriteViewModel.deleteAllFavoriteShows()
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}