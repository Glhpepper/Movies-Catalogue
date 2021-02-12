package com.example.moviescatalogue.detail.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.detail.ProductionAdapter
import com.example.core.utils.Constants.Companion.DETAIL_MOVIES_BUNDLE
import com.example.moviescatalogue.R
import com.example.moviescatalogue.databinding.FragmentProductionMoviesBinding
import com.example.moviescatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductionMoviesFragment : Fragment() {
    private var _binding: FragmentProductionMoviesBinding? = null
    private val binding get() = _binding

    private val detailMoviesViewModel: DetailMoviesViewModel by viewModels()

    @Inject
    lateinit var productionAdapter: ProductionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductionMoviesBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProduction()

        binding?.let {
            with(it.productionRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = productionAdapter
            }
        }
    }

    private fun getProduction() {
        val id = arguments?.getString(DETAIL_MOVIES_BUNDLE)
        if (id != null) {
            detailMoviesViewModel.getDetailMovies(id).observe(viewLifecycleOwner, { production ->
                if (production != null) {
                    when (production) {
                        is Resource.Loading -> showShimmerEffect(true)
                        is Resource.Success -> {
                            showShimmerEffect(false)
                            val productionCompany =
                                DataMapper.mapProductionCompanyToDomain(production.data?.productionCompanies)
                            productionAdapter.submitList(productionCompany)
                            if (production.data?.productionCompanies?.isEmpty() == true) {
                                binding?.apply {
                                    errorImgViewProduction.visibility = View.VISIBLE
                                    setErrorImage(false)
                                    errorTextViewProduction.visibility = View.VISIBLE
                                    errorTextViewProduction.text =
                                        getString(R.string.no_production)
                                }
                            }
                        }
                        is Resource.Error -> {
                            showShimmerEffect(false)
                            binding?.apply {
                                errorImgViewProduction.visibility = View.VISIBLE
                                setErrorImage(true)
                                errorTextViewProduction.visibility = View.VISIBLE
                                errorTextViewProduction.text =
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
            binding?.errorImgViewProduction?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_sad
                )
            )
        } else {
            binding?.errorImgViewProduction?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_no_data
                )
            )
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding?.productionRecyclerView?.showShimmer()
        } else {
            binding?.productionRecyclerView?.hideShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.productionRecyclerView?.adapter = null
    }
}