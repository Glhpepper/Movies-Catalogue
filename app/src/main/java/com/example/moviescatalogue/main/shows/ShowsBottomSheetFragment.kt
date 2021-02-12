package com.example.moviescatalogue.main.shows

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
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_SHOWS
import com.example.core.utils.Constants.Companion.DEFAULT_PREFERENCE_ID
import com.example.core.utils.Constants.Companion.DEFAULT_YEAR
import com.example.core.utils.Constants.Companion.DOCUMENTARY_GENRE
import com.example.core.utils.Constants.Companion.DRAMA_GENRE
import com.example.core.utils.Constants.Companion.FAMILY_GENRE
import com.example.core.utils.Constants.Companion.KIDS_GENRE
import com.example.core.utils.Constants.Companion.MYSTERY_GENRE
import com.example.core.utils.Constants.Companion.POLITICS_GENRE
import com.example.moviescatalogue.databinding.FragmentShowsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentShowsBottomSheetBinding? = null
    private val binding get() = _binding

    private val showsViewModel: ShowsViewModel by viewModels()

    private var genreChip = DEFAULT_GENRE_SHOWS
    private var genreChipId = DEFAULT_PREFERENCE_ID
    private var yearChip = DEFAULT_YEAR
    private var yearChipId = DEFAULT_PREFERENCE_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowsBottomSheetBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showsViewModel.readGenreAndYear().observe(viewLifecycleOwner, { value ->
            genreChip = value.selectedGenre
            yearChip = value.selectedYear
            binding?.genreChipGroupShows?.let { updateChip(value.selectedGenreId, it) }
            binding?.yearChipGroupShows?.let { updateChip(value.selectedYearId, it) }
        })

        checkChipGroup()

        binding?.applyBtnShows?.setOnClickListener {
            showsViewModel.saveGenreAndYear(
                genreChip, genreChipId, yearChip, yearChipId
            )
            val action =
                ShowsBottomSheetFragmentDirections.actionShowsBottomSheetFragmentToShowsFragment()
            findNavController().navigate(action)
        }
    }

    private fun checkChipGroup() {
        binding?.genreChipGroupShows?.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedGenre = when (chip.text) {
                getString(R.string.animation) -> ANIMATION_GENRE
                getString(R.string.comedy) -> COMEDY_GENRE
                getString(R.string.crime) -> CRIME_GENRE
                getString(R.string.documentary) -> DOCUMENTARY_GENRE
                getString(R.string.drama) -> DRAMA_GENRE
                getString(R.string.kids) -> KIDS_GENRE
                getString(R.string.politics) -> POLITICS_GENRE
                getString(R.string.mystery) -> MYSTERY_GENRE
                getString(R.string.family) -> FAMILY_GENRE
                else -> DEFAULT_GENRE_SHOWS
            }
            genreChip = selectedGenre
            genreChipId = checkedId
        }

        binding?.yearChipGroupShows?.setOnCheckedChangeListener { group, checkedId ->
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