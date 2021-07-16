package com.example.playground.ui.instrument

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playground.R
import com.example.playground.extensions.asImmutable
import com.example.playground.ui.instrument.model.InstrumentModel
import com.example.playground.ui.instrument.model.InstrumentModel.Type

class InstrumentViewModel : ViewModel() {

    private val _instrumentLiveData = MutableLiveData<InstrumentModel>()
    val instrumentLiveData = _instrumentLiveData.asImmutable()

    fun callGetInstrument() {
        _instrumentLiveData.value = InstrumentModel(
            name = "Les Paul",
            type = Type.GUITAR,
            resId = R.drawable.guitarra_les_paul
        )
    }
}