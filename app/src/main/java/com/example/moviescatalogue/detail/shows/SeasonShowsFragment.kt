package com.example.moviescatalogue.detail.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.detail.SeasonAdapter
import com.example.core.utils.Constants.Companion.DETAIL_SHOWS_BUNDLE
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentSeasonShowsBinding
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeasonShowsFragment : Fragment() {
    private var _binding: FragmentSeasonShowsBinding? = null
    private val binding get() = _binding

    private val detailShowsViewModel: DetailShowsViewModel by viewModels()

    @Inject
    lateinit var seasonAdapter: SeasonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeasonShowsBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSeason()

        binding?.let {
            with(it.seasonRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = seasonAdapter
            }
        }
    }

    private fun getSeason() {
        val id = arguments?.getString(DETAIL_SHOWS_BUNDLE)
        if (id != null) {
            detailShowsViewModel.getDetailShows(id).observe(viewLifecycleOwner, { season ->
                if (season != null) {
                    when (season) {
                        is Resource.Loading -> showShimmerEffect(true)
                        is Resource.Success -> {
                            showShimmerEffect(false)
                            val seasonItem =
                                DataMapper.mapSeasonItemToDomain(season.data?.seasons)
                            seasonAdapter.submitList(seasonItem)
                            if (season.data?.seasons?.isEmpty() == true) {
                                binding?.apply {
                                    errorImgViewSeason.visibility = View.VISIBLE
                                    setErrorImage(false)
                                    errorTextViewSeason.visibility = View.VISIBLE
                                    errorTextViewSeason.text =
                                        getString(R.string.no_season)
                                }
                            }
                        }
                        is Resource.Error -> {
                            showShimmerEffect(false)
                            binding?.apply {
                                errorImgViewSeason.visibility = View.VISIBLE
                                setErrorImage(true)
                                errorTextViewSeason.visibility = View.VISIBLE
                                errorTextViewSeason.text =
                                    getString(R.string.something_wrong)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setErrorImage(show: Boolean) {
        if (show) {
            binding?.errorImgViewSeason?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_sad
                )
            )
        } else {
            binding?.errorImgViewSeason?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_no_data
                )
            )
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.seasonRecyclerView?.showShimmer()
        } else {
            binding?.seasonRecyclerView?.hideShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.seasonRecyclerView?.adapter = null
    }
}