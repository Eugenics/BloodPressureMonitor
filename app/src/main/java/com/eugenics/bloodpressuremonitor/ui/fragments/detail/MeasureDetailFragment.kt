package com.eugenics.bloodpressuremonitor.ui.fragments.detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.databinding.MeasureDetailFragmentBinding
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.common.showShortSnackBar
import com.eugenics.bloodpressuremonitor.ui.common.themeColor
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasureDetailFragment : Fragment(R.layout.measure_detail_fragment) {

    private val viewModel: MeasureDetailViewModel by viewModels()
    private val binding: MeasureDetailFragmentBinding by viewBinding()
    private val args: MeasureDetailFragmentArgs by navArgs()

    private var validFlag: Boolean = true
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        enterTransition = MaterialFadeThrough().apply {
//            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toolbar: MaterialToolbar = requireActivity().findViewById(R.id.main_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        toolbar.setNavigationOnClickListener {
            if (isEdit()) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.value_changed))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        if (validate()) {
                            saveMeasure()
                        }
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        hideInputKeyboard()
                        findNavController().navigateUp()
                    }
                    .create()
                    .show()
            } else {
                hideInputKeyboard()
                findNavController().navigateUp()
            }
        }

        val measureUID = args.measureId
        viewModel.fetchMeasureById(measureUID)

        launchCoroutineScopes()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
            addTarget(R.id.measure_detail_card_view)
        }
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.measureDetailCardView
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext()
                .themeColor(com.google.android.material.R.attr.colorSurface)
            startContainerColor = requireContext()
                .themeColor(com.google.android.material.R.attr.colorSecondary)
            endContainerColor = requireContext()
                .themeColor(com.google.android.material.R.attr.colorSurface)
        }

        setObservables()
        setListeners()
        setValues()
        setEditTextValidators()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.delete -> {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.delete_note))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.deleteMeasure()
                        hideInputKeyboard()
                        findNavController().navigateUp()
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .create()
                    .show()

                true
            }
            else -> false
        }

    private fun launchCoroutineScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.measureNote.collect {
                viewModel.measure = it
                setValues()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.savaState.collect { saveState ->
                if (saveState) {
                    hideInputKeyboard()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun setObservables() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
//            binding.root.showShortSnackBar(message)
        }
    }

    private fun setValues() {
        val upperValue =
            if (viewModel.measure?.upperValue == 0) "" else viewModel.measure?.upperValue.toString()
        val downValue =
            if (viewModel.measure?.downValue == 0) "" else viewModel.measure?.downValue.toString()
        val heartRate =
            if (viewModel.measure?.heartRate == 0) "" else viewModel.measure?.heartRate.toString()

        binding.upperValueDetail.editText?.setText(upperValue)
        binding.downValueDetail.editText?.setText(downValue)
        binding.heartRateDetail.editText?.setText(heartRate)
    }

    private fun setListeners() {
        binding.heartRateEditText.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                return@OnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE
                    && validate()
                ) {
                    saveMeasure()
                    true
                } else {
                    false
                }
            }
        )
    }

    private fun saveMeasure() {
        viewModel.saveMeasure(
            binding.upperEditText.text.toString().toInt(),
            binding.downEditText.text.toString().toInt(),
            binding.heartRateEditText.text.toString().toInt()
        )
        hideInputKeyboard()
        findNavController().navigateUp()
    }

    private fun setEditTextValidators() {
        binding.upperEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                upperValidate(binding.upperEditText.text.toString())
            }
        }
        binding.downEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                downValidate(binding.downEditText.text.toString())
            }
        }
        binding.heartRateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                heartRateValidate(binding.heartRateEditText.text.toString())
            }
        }
    }

    private fun validate(): Boolean {
        upperValidate(binding.upperEditText.text.toString())
        downValidate(binding.downEditText.text.toString())
        heartRateValidate(binding.heartRateEditText.text.toString())

        validFlag = binding.upperValueDetail.error == null
                && binding.downValueDetail.error == null
                && binding.heartRateDetail.error == null

        return validFlag
    }

    private fun isEdit(): Boolean {
        if (
            (binding.upperEditText.text.isNullOrEmpty()
                    && binding.downEditText.text.isNullOrEmpty()
                    && binding.heartRateEditText.text.isNullOrEmpty())
            &&
            (viewModel.measure?.upperValue == 0
                    && viewModel.measure?.downValue == 0
                    && viewModel.measure?.heartRate == 0)
        ) {
            isEdit = false
        } else {
            isEdit =
                (binding.upperEditText.text.toString() != viewModel.measure?.upperValue.toString()
                        || binding.downEditText.text.toString() != viewModel.measure?.downValue.toString()
                        || binding.heartRateEditText.text.toString() != viewModel.measure?.heartRate.toString())
        }
        return isEdit
    }

    private fun upperValidate(value: String): String? {
        val errorString = when {
            value.isBlank() -> getString(R.string.required)
            value.isEmpty() -> getString(R.string.required)
            value.toInt() < 60 -> {
                getString(R.string.low_value)
            }
            value.toInt() > 300 -> {
                getString(R.string.height_value)

            }
            else -> {
                null
            }
        }
        binding.upperValueDetail.error = errorString
        return errorString
    }

    private fun downValidate(value: String): String? {
        val errorString = when {
            value.isBlank() -> getString(R.string.required)
            value.isEmpty() -> getString(R.string.required)
            value.toInt() < 30 -> {
                getString(R.string.low_value)
            }
            value.toInt() > 240 -> {
                getString(R.string.height_value)
            }
            else -> {
                null
            }
        }
        binding.downValueDetail.error = errorString
        return errorString
    }

    private fun heartRateValidate(value: String): String? {
        val errorString = when {
            value.isBlank() -> getString(R.string.required)
            value.isEmpty() -> getString(R.string.required)
            value.toInt() < 40 -> {
                getString(R.string.low_value)
            }
            value.toInt() > 200 -> {
                getString(R.string.height_value)
            }
            else -> {
                null
            }
        }
        binding.heartRateDetail.error = errorString
        return errorString
    }

    private fun hideInputKeyboard() {
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    companion object {
        const val TAG = "MEASURE_DETAIL_FRAGMENT"
        const val KEY = "BLOOD_PRESSURE_OBJECT"

        @JvmStatic
        fun newInstance(measure: BloodPressureModel? = null) =
            MeasureDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY, measure)
                }
            }
    }
}