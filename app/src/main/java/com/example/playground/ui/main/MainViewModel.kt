package com.example.playground.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _mostrar = MutableLiveData<Boolean>()
    private val _musicasLiveData = MutableLiveData<List<MusicEntity>>()
    private val _musicDeleted = MutableLiveData<Long>()

    val musicasLiveData: LiveData<List<MusicEntity>> get() = _musicasLiveData
    val mostrar : LiveData<Boolean> get() = _mostrar
    val musicDeleted : LiveData<Long> get() = _musicDeleted

    init {
        _mostrar.value = false
    }

    fun onClickButtonMostrar(){
        Log.i("MAIN_VIEW_MODEL", "Clicou ButtonMostrar")
        _mostrar.value = !_mostrar.value!!
    }

    fun loadMusics(){
        Log.i("MAIN_VIEW_MODEL", "Clicou Button1")
        viewModelScope.launch {
            val musicas = async {
                musicRepository.getAllMusics()
            }.await()
            _musicasLiveData.value = musicas
        }
    }

    fun deleteMusic(musicEntity: MusicEntity){
        Log.i("MAIN_VIEW_MODEL", "Clicou Excluir música")
        viewModelScope.launch {
            val result = async{
                musicRepository.deleteMusic(musicEntity.id)
            }.await()
            _musicDeleted.value = musicEntity.id
        }

    }
}