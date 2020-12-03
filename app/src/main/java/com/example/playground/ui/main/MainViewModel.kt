package com.example.playground.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.*

class MainViewModel(
    private val repository: MainRepository,
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _allMusicsEvent = musicRepository.getAllMusics()

    val allMusicsEvent : LiveData<List<MusicEntity>> get() = _allMusicsEvent

    fun refreshMusics(){

    }

    fun deleteMusic(musicEntity: MusicEntity){
        Log.i("MAIN_VIEW_MODEL", "Clicou Excluir música")
        viewModelScope.launch {
            musicRepository.deleteMusic(musicEntity.id)
        }

    }
}