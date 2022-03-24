package com.eugenics.bloodpressuremonitor.ui.fragments.main

import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.databinding.MainFragmentBinding
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.fragments.main.adapter.MainAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment), MainAdapter.Delegate {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val adapter: MainAdapter = MainAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            viewModel.dataListStateFlow.collect { dataList ->
                adapter.submitList(dataList)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bloodPressureList.adapter = adapter

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        viewModel.fetchData()
        binding.fab.setOnClickListener {
            val card = MaterialCardView(requireContext()).apply {
                transitionName = getString(R.string.blood_pressure_card_transition_name)
            }
            onElementClick(null, card)
        }
    }

    companion object {
        const val TAG = "MAIN_FRAGMENT"

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onElementClick(measure: BloodPressureModel?, view: View) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }

        val measureCardDetailTransitionName = getString(R.string.measure_detail_transition_name)

        val extras = FragmentNavigatorExtras(view to measureCardDetailTransitionName)


        val direction =
            MainFragmentDirections.actionMainFragmentToMeasureDetailFragment(measure?.uid ?: "0")
        findNavController().navigate(direction, extras)
    }

}