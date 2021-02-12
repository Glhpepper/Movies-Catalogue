package com.example.moviescatalogue.detail.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviescatalogue.R
import com.example.core.data.Resource
import com.example.core.databinding.FragmentOverviewShowsBinding
import com.example.core.utils.Constants.Companion.ANIMATION_GENRE
import com.example.core.utils.Constants.Companion.COMEDY_GENRE
import com.example.core.utils.Constants.Companion.CRIME_GENRE
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_SHOWS
import com.example.core.utils.Constants.Companion.DETAIL_SHOWS_BUNDLE
import com.example.core.utils.Constants.Companion.DOCUMENTARY_GENRE
import com.example.core.utils.Constants.Companion.DRAMA_GENRE
import com.example.core.utils.Constants.Companion.FAMILY_GENRE
import com.example.core.utils.Constants.Companion.KIDS_GENRE
import com.example.core.utils.Constants.Companion.MYSTERY_GENRE
import com.example.core.utils.Constants.Companion.POLITICS_GENRE
import com.example.moviescatalogue.model.detail.shows.DetailShows
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewShowsFragment : Fragment() {
    private var _binding: FragmentOverviewShowsBinding? = null
    private val binding get() = _binding

    private val detailShowsViewModel: DetailShowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewShowsBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailShows()
    }

    private fun setupDetailShows() {
        val arg = arguments
        val id = arg?.getString(DETAIL_SHOWS_BUNDLE)
        if (id != null) {
            detailShowsViewModel.getDetailShows(id)
                .observe(viewLifecycleOwner, { shows ->
                    if (shows != null) {
                        when (shows) {
                            is Resource.Loading -> {
                                binding?.showsPb?.visibility = View.VISIBLE
                                hideContent(true)
                            }
                            is Resource.Success -> {
                                binding?.showsPb?.visibility = View.INVISIBLE
                                hideContent(false)
                                val showsDetail = DataMapper.mapDetailShowsToDetailShowsDomain(shows.data)
                                binding?.detailShows = showsDetail
                                setupGenreMovies(shows.data)
                            }
                            is Resource.Error -> {
                                binding?.showsPb?.visibility = View.INVISIBLE
                                hideContent(true)
                                binding?.apply {
                                    errorImgViewShows.visibility = View.VISIBLE
                                    errorTextViewShows.visibility = View.VISIBLE
                                    errorTextViewShows.text =
                                        getString(R.string.something_wrong)
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun hideContent(hide: Boolean) {
        if (hide) {
            binding?.apply {
                contentDetailShows.visibility = View.INVISIBLE
            }
        } else {
            binding?.apply {
                contentDetailShows.visibility = View.VISIBLE
            }
        }
    }

    private fun setupGenreMovies(data: DetailShows?) {
        if (data != null) {
            data.genres?.forEach { type ->
                when (type.id) {
                    DEFAULT_GENRE_SHOWS.toInt() -> {
                        binding?.actionImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.actionTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    ANIMATION_GENRE.toInt() -> {
                        binding?.animeImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.animeTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    COMEDY_GENRE.toInt() -> {
                        binding?.comedyImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.comedyTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    CRIME_GENRE.toInt() -> {
                        binding?.crimeImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.crimeTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    DOCUMENTARY_GENRE.toInt() -> {
                        binding?.documentaryImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.documentaryTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    DRAMA_GENRE.toInt() -> {
                        binding?.dramaImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.dramaTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    KIDS_GENRE.toInt() -> {
                        binding?.kidsImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.kidsTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    POLITICS_GENRE.toInt() -> {
                        binding?.politicsImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.politicsTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    MYSTERY_GENRE.toInt() -> {
                        binding?.mysteryImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.mysteryTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    FAMILY_GENRE.toInt() -> {
                        binding?.familyImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.familyTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}