package com.example.moviescatalogue.upcoming

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.core.ui.detail.ViewPagerAdapter
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentUpcomingBinding
import com.example.moviescatalogue.upcoming.movies.UpcomingMoviesFragment
import com.example.moviescatalogue.upcoming.shows.UpcomingShowsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setHasOptionsMenu(true)
    }

    private fun setupView() {
        val fragment = ArrayList<Fragment>()
        fragment.add(UpcomingMoviesFragment())
        fragment.add(UpcomingShowsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.movies_fragment))
        titles.add(getString(R.string.shows_fragment))

        val pagerAdapter = ViewPagerAdapter(null, fragment, childFragmentManager, lifecycle)

        binding?.viewPagerUpcoming?.adapter = pagerAdapter

        binding?.tabUpcomingMovies?.let { tabLayout ->
            binding?.viewPagerUpcoming?.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = titles[position]
                }.attach()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuFavorite = menu.findItem(R.id.main_favorite)
        menuFavorite?.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.green))

        val menuSearch = menu.findItem(R.id.search)
        menuSearch.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}