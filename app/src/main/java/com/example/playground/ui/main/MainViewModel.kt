package com.example.playground.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.LiveDataSingleEvent
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.repository.MainRepositoryMock
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _allMusicsEvent = musicRepository.getAllMusics()
    private val _repositoryResponse = MutableLiveData<LiveDataSingleEvent<ResponseModel<MusicEntity>>>()

    val allMusicsEvent : LiveData<List<MusicEntity>> get() = _allMusicsEvent
    val repositoryReponse : MutableLiveData<LiveDataSingleEvent<ResponseModel<MusicEntity>>> get() = _repositoryResponse


    fun deleteMusic(id: Long) = viewModelScope.launch {
        try {
            val backup = musicRepository.getMusic(id).firstOrNull()
            musicRepository.deleteMusic(id)
            _repositoryResponse.value = LiveDataSingleEvent(ResponseModel.DELETE(s = true, m = backup))
        } catch (e: Exception){
            _repositoryResponse.value = LiveDataSingleEvent(ResponseModel.DELETE(s = false, e = e.message))
        }
    }

    fun restoreMusicFromDelete(id: Long, name: String, artist: String?) = viewModelScope.launch {
        try {
            musicRepository.restoreMusic(id, name, artist)
            _repositoryResponse.value = LiveDataSingleEvent(ResponseModel.RESTORE(s = true))
        } catch (e: Exception){
            _repositoryResponse.value = LiveDataSingleEvent(ResponseModel.RESTORE(s = false, e = e.message))
        }
    }
}