package com.example.moviescatalogue.detail.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviescatalogue.R
import com.example.core.data.Resource
import com.example.core.databinding.FragmentOverviewMoviesBinding
import com.example.core.utils.Constants.Companion.ANIMATION_GENRE
import com.example.core.utils.Constants.Companion.COMEDY_GENRE
import com.example.core.utils.Constants.Companion.CRIME_GENRE
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_MOVIES
import com.example.core.utils.Constants.Companion.DETAIL_MOVIES_BUNDLE
import com.example.core.utils.Constants.Companion.FAMILY_GENRE
import com.example.core.utils.Constants.Companion.HISTORY_GENRE
import com.example.core.utils.Constants.Companion.HORROR_GENRE
import com.example.core.utils.Constants.Companion.MUSIC_GENRE
import com.example.core.utils.Constants.Companion.ROMANCE_GENRE
import com.example.core.utils.Constants.Companion.WAR_GENRE
import com.example.moviescatalogue.model.detail.movies.DetailMovies
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewMoviesFragment : Fragment() {
    private var _binding: FragmentOverviewMoviesBinding? = null
    private val binding get() = _binding

    private val detailMoviesViewModel: DetailMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewMoviesBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailMovies()
    }

    private fun setupDetailMovies() {
        val arg = arguments
        val id = arg?.getString(DETAIL_MOVIES_BUNDLE)
        if (id != null) {
            detailMoviesViewModel.getDetailMovies(id)
                .observe(viewLifecycleOwner, { movies ->
                    if (movies != null) {
                        when (movies) {
                            is Resource.Loading -> {
                                binding?.moviesPb?.visibility = View.VISIBLE
                                hideContent(true)
                            }
                            is Resource.Success -> {
                                binding?.moviesPb?.visibility = View.INVISIBLE
                                hideContent(false)
                                val moviesDetail = DataMapper.mapDetailMoviesToDetailMoviesDomain(movies.data)
                                binding?.detailMovies = moviesDetail
                                setupGenreMovies(movies.data)
                            }
                            is Resource.Error -> {
                                binding?.moviesPb?.visibility = View.INVISIBLE
                                hideContent(true)
                                binding?.apply {
                                    errorImgViewMovies.visibility = View.VISIBLE
                                    errorTextViewMovies.visibility = View.VISIBLE
                                    errorTextViewMovies.text =
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
                contentDetailMovies.visibility = View.INVISIBLE
            }
        } else {
            binding?.apply {
                contentDetailMovies.visibility = View.VISIBLE
            }
        }
    }

    private fun setupGenreMovies(data: DetailMovies?) {
        if (data != null) {
            data.genres?.forEach { type ->
                when (type.id) {
                    DEFAULT_GENRE_MOVIES.toInt() -> {
                        binding?.actionImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.actionTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    ANIMATION_GENRE.toInt() -> {
                        binding?.animationImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.animationTextView?.setTextColor(
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
                    HISTORY_GENRE.toInt() -> {
                        binding?.historyImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.historyTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    HORROR_GENRE.toInt() -> {
                        binding?.horrorImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.horrorTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    ROMANCE_GENRE.toInt() -> {
                        binding?.romanceImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.romanceTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    WAR_GENRE.toInt() -> {
                        binding?.warImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.warTextView?.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    MUSIC_GENRE.toInt() -> {
                        binding?.musicImgView?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        binding?.musicTextView?.setTextColor(
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