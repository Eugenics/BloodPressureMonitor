package com.eugenics.bloodpressuremonitor.ui.fragments.main

import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.databinding.MainFragmentBinding
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.compose.main.MainJornal
import com.eugenics.bloodpressuremonitor.ui.compose.theme.BloodPressureMonitorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.mainComposeView.setContent {
            BloodPressureMonitorTheme {
                MainJornal(viewModel) { data ->
                    onCardClick(data)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        viewModel.fetchData()
        binding.fab.setOnClickListener {
            showMeasureDetail(null)
        }
    }

    private fun onCardClick(measure: BloodPressureModel?) {
        showMeasureDetail(measure)
    }

    private fun showMeasureDetail(measure: BloodPressureModel?) {
        val direction =
            MainFragmentDirections.actionMainFragmentToMeasureDetailFragment(measure?.uid ?: "0")

        findNavController().navigate(direction)
    }

    companion object {
        const val TAG = "MAIN_FRAGMENT"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}