package com.example.moviescatalogue.upcoming.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.upcoming.UpcomingShowsAdapter
import com.example.core.utils.Constants.Companion.ANIMATION_GENRE
import com.example.core.utils.Constants.Companion.API_KEY
import com.example.core.utils.Constants.Companion.PAGE
import com.example.core.utils.Constants.Companion.QUERY_API_KEY
import com.example.core.utils.Constants.Companion.QUERY_GENRE
import com.example.core.utils.Constants.Companion.QUERY_PAGE
import com.example.core.utils.Constants.Companion.QUERY_SORT_BY
import com.example.core.utils.Constants.Companion.QUERY_YEAR_UPCOMING_SHOWS
import com.example.core.utils.Constants.Companion.UPCOMING_SHORT_SHOWS
import com.example.core.utils.Constants.Companion.UPCOMING_YEAR
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentUpcomingShowsBinding
import com.example.moviescatalogue.upcoming.UpcomingFragmentDirections
import com.example.moviescatalogue.upcoming.UpcomingViewModel
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingShowsFragment : Fragment() {
    private var _binding: FragmentUpcomingShowsBinding? = null
    private val binding get() = _binding

    private val upcomingViewModel: UpcomingViewModel by viewModels()
    private val queries: HashMap<String, String> = HashMap()

    private lateinit var upcomingShowsAdapter: UpcomingShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingShowsBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingShowsAdapter = UpcomingShowsAdapter(UpcomingShowsAdapter.OnClickListener {
            val action =
                UpcomingFragmentDirections.actionUpcomingFragmentToDetailShowsActivity(it.showsId)
            findNavController().navigate(action)
        })

        binding?.let {
            with(it.upcomingShowsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = upcomingShowsAdapter
            }
        }

        getUpcomingShows()
    }

    private fun getUpcomingShows() {
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_SORT_BY] = UPCOMING_SHORT_SHOWS
        queries[QUERY_PAGE] = PAGE
        queries[QUERY_YEAR_UPCOMING_SHOWS] = UPCOMING_YEAR
        queries[QUERY_GENRE] = ANIMATION_GENRE
        upcomingViewModel.getUpcomingShows(queries).observe(viewLifecycleOwner, { upcoming ->
            if (upcoming != null) {
                when (upcoming) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        val showsList = DataMapper.mapShowsToShowsDomain(upcoming.data)
                        upcomingShowsAdapter.submitList(showsList)
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        binding?.apply {
                            errorImgViewUpcomingShows.visibility = View.VISIBLE
                            errorTextViewUpcomingShows.visibility = View.VISIBLE
                            upcomingShowsRecyclerView.visibility = View.INVISIBLE
                            errorTextViewUpcomingShows.text = getString(R.string.something_wrong)
                        }
                    }
                }
            }
        })
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.upcomingShowsRecyclerView?.showShimmer()
        } else {
            binding?.upcomingShowsRecyclerView?.hideShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.upcomingShowsRecyclerView?.adapter = null
        _binding = null
    }
}