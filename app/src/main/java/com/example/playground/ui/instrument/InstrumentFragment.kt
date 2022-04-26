package com.example.playground.ui.instrument

import androidx.core.view.isVisible
import com.example.playground.R
import com.example.playground.codetest.toCapitalizeWords
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.InstrumentFragmentBinding
import com.example.playground.extensions.gone
import com.example.playground.extensions.observe
import com.example.playground.extensions.visible
import com.example.playground.ui.instrument.model.InstrumentModel
import com.example.playground.utils.DialogImageViewer
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentFragment :
    CoreFragment<InstrumentFragmentBinding>(InstrumentFragmentBinding::inflate) {

    private val viewModel: InstrumentViewModel by viewModel()

    override fun setupViews() {
        binding.progressInstrument.visible()
        binding.llInstrumentContainer.gone()

        viewModel.callGetInstrument()

        binding.ivPhoto.setOnClickListener {
            DialogImageViewer(
                activity = requireActivity(),
                resId = R.drawable.guitarra_les_paul
            ).show()
        }

        binding.btnShow.setOnClickListener {
            val visible = binding.ivPhoto.isVisible
            if (visible) {
                binding.ivPhoto.gone()
                binding.btnShow.text = "Show"
            } else {
                binding.ivPhoto.visible()
                binding.btnShow.text = "Hide"
            }
        }
    }

    override fun setupObservers() {
        observe(viewModel.instrumentLiveData) {
            render(it)
        }
    }

    fun render(model: InstrumentModel) = with(binding) {
        progressInstrument.gone()
        llInstrumentContainer.visible()
        tvName.text = model.name
        tvType.text = model.type.name.toCapitalizeWords()
        ivPhoto.setImageResource(model.resId)
    }
}