package com.example.playground.ui.instrument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playground.R
import com.example.playground.codetest.toCapitalizeWords
import com.example.playground.extensions.changeVisibility
import com.example.playground.extensions.gone
import com.example.playground.extensions.observe
import com.example.playground.extensions.visible
import com.example.playground.ui.instrument.model.InstrumentModel
import kotlinx.android.synthetic.main.instrument_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentFragment : Fragment() {

    private val viewModel: InstrumentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.instrument_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressInstrument.visible()
        llInstrumentContainer.gone()

        viewModel.callGetInstrument()

        btnShow.setOnClickListener {
            val visible = ivPhoto.isVisible
            if(visible){
                ivPhoto.gone()
                btnShow.text = "Show"
            } else {
                ivPhoto.visible()
                btnShow.text = "Hide"
            }
        }

        observe(viewModel.instrumentLiveData) {
            render(it)
        }
    }

    fun render(model: InstrumentModel) {
        progressInstrument.gone()
        llInstrumentContainer.visible()
        tvName.text = model.name
        tvType.text = model.type.name.toCapitalizeWords()
        ivPhoto.setImageResource(model.resId)
    }
}