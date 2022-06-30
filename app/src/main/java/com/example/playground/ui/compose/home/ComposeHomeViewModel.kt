package com.example.playground.ui.compose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.repository.SampleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class ComposeHomeViewModel : ViewModel() {

    private val list = SampleData.musicsModel.toMutableList()

    private val _state: MutableStateFlow<ComposeHomeState> = MutableStateFlow(Empty)
    val state: StateFlow<ComposeHomeState> = _state

    private fun getSuccessChance() = (0..2).random() != 0

    fun fetchList() {
        _state.update { Loading }
        viewModelScope.launch {
            delay(2000)
            _state.update {
                Success(list)
            }
        }
    }

    fun addItem() {
        _state.update { Loading }
        viewModelScope.launch {
            delay(1000)
            if(getSuccessChance()) {
                list.add(0, list.random().copy(id = Random().nextLong()))
                _state.update {
                    Success(list)
                }
            } else {
                _state.update {
                    Error
                }
            }
        }
    }
}