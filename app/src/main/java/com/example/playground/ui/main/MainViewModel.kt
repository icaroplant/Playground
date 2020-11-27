package com.example.playground.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _mostrar = MutableLiveData<Boolean>()
    private val _musicasLiveData = MutableLiveData<MutableList<Musica>>()

    val musicasLiveData: LiveData<MutableList<Musica>> get() = _musicasLiveData
    val mostrar : LiveData<Boolean> get() = _mostrar

    init {
        _mostrar.value = false
    }

    fun onClickButtonMostrar(){
        Log.i("MAIN_VIEW_MODEL", "Clicou ButtonMostrar")
        _mostrar.value = !_mostrar.value!!
    }

    fun onClickButton1(){
        Log.i("MAIN_VIEW_MODEL", "Clicou Button1")
        viewModelScope.launch {
            val musicas = async {
                repository.getMusicas()
            }.await()
            _musicasLiveData.value = musicas
        }
    }

    fun onClickButton2(){
        Log.i("MAIN_VIEW_MODEL", "Clicou Button2")

    }

    fun onClickButton3(){
        Log.i("MAIN_VIEW_MODEL", "Clicou Button3")
    }
}