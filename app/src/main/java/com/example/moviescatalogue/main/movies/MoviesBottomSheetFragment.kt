package com.example.moviescatalogue.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviescatalogue.R
import com.example.core.utils.Constants.Companion.ANIMATION_GENRE
import com.example.core.utils.Constants.Companion.COMEDY_GENRE
import com.example.core.utils.Constants.Companion.CRIME_GENRE
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_MOVIES
import com.example.core.utils.Constants.Companion.DEFAULT_PREFERENCE_ID
import com.example.core.utils.Constants.Companion.DEFAULT_YEAR
import com.example.core.utils.Constants.Companion.FAMILY_GENRE
import com.example.core.utils.Constants.Companion.HISTORY_GENRE
import com.example.core.utils.Constants.Companion.HORROR_GENRE
import com.example.core.utils.Constants.Companion.MUSIC_GENRE
import com.example.core.utils.Constants.Companion.ROMANCE_GENRE
import com.example.core.utils.Constants.Companion.WAR_GENRE
import com.example.moviescatalogue.databinding.FragmentMoviesBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMoviesBottomSheetBinding? = null
    private val binding get() = _binding

    private val moviesViewModel: MoviesViewModel by viewModels()

    private var genreChip = DEFAULT_GENRE_MOVIES
    private var genreChipId = DEFAULT_PREFERENCE_ID
    private var yearChip = DEFAULT_YEAR
    private var yearChipId = DEFAULT_PREFERENCE_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBottomSheetBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel.readGenreAndYear().observe(viewLifecycleOwner, { value ->
            genreChip = value.selectedGenre
            yearChip = value.selectedYear
            binding?.genreChipGroupMovies?.let { updateChip(value.selectedGenreId, it) }
            binding?.yearChipGroupMovies?.let { updateChip(value.selectedYearId, it) }
        })

        checkChipGroup()

        binding?.applyBtnMovies?.setOnClickListener {
            moviesViewModel.saveGenreAndYear(
                genreChip, genreChipId, yearChip, yearChipId
            )
            val action =
                MoviesBottomSheetFragmentDirections.actionMoviesBottomSheetFragmentToMoviesFragment()
            findNavController().navigate(action)
        }
    }

    private fun checkChipGroup() {
        binding?.genreChipGroupMovies?.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedGenre = when (chip.text) {
                getString(R.string.animation) -> ANIMATION_GENRE
                getString(R.string.comedy) -> COMEDY_GENRE
                getString(R.string.crime) -> CRIME_GENRE
                getString(R.string.history) -> HISTORY_GENRE
                getString(R.string.horror) -> HORROR_GENRE
                getString(R.string.romance) -> ROMANCE_GENRE
                getString(R.string.war) -> WAR_GENRE
                getString(R.string.music) -> MUSIC_GENRE
                getString(R.string.family) -> FAMILY_GENRE
                else -> DEFAULT_GENRE_MOVIES
            }
            genreChip = selectedGenre
            genreChipId = checkedId
        }

        binding?.yearChipGroupMovies?.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedYear = chip.text.toString()
            yearChip = selectedYear
            yearChipId = checkedId
        }
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            chipGroup.findViewById<Chip>(chipId).isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}