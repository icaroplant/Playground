package com.example.playground.ui.manage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ManageMusicViewModel(
    val musicRepository: MusicRepository
): ViewModel()  {
    private val _repositoryResponse = MutableLiveData<ResponseModel<MusicEntity>>()

    val repositoryReponse : MutableLiveData<ResponseModel<MusicEntity>> get() = _repositoryResponse

    companion object{
        private val TAG = SaveMusicViewModel::class.java.simpleName
    }

    fun saveMusic(name: String, artist: String?) = viewModelScope.launch {
        try {
            val id = musicRepository.insertMusic(name, artist)
            if(id > 0){
                _repositoryResponse.value = ResponseModel.INSERT(s = true, m = MusicEntity(id, name, artist))
            } else{
                _repositoryResponse.value = ResponseModel.INSERT(s = false, e = "ERRO NO INSERT")
            }
        } catch (e: Exception){
            _repositoryResponse.value = ResponseModel.INSERT(s = false, e = e.message)
        }
    }

    fun updateMusic(id: Long, name: String, artist: String?) = viewModelScope.launch {
        try {
            val backup = musicRepository.getMusic(id).firstOrNull()
            musicRepository.updateMusic(id, name, artist)
            _repositoryResponse.value = ResponseModel.UPDATE(s = true, m = backup)
        } catch (e: Exception){
            _repositoryResponse.value = ResponseModel.UPDATE(s = false, e = e.message)
        }
    }

    fun deleteMusic(id: Long) = viewModelScope.launch {
        try {
            val backup = musicRepository.getMusic(id).firstOrNull()
            musicRepository.deleteMusic(id)
            _repositoryResponse.value = ResponseModel.DELETE(s = true, m = backup)
        } catch (e: Exception){
            _repositoryResponse.value = ResponseModel.DELETE(s = false, e = e.message)
        }
    }

    fun restoreMusicFromUpdate(id: Long, name: String, artist: String?) = viewModelScope.launch {
        try {
            musicRepository.updateMusic(id, name, artist)
            _repositoryResponse.value = ResponseModel.RESTORE(s = true, m = MusicEntity(id, name, artist))
        } catch (e: Exception){
            _repositoryResponse.value = ResponseModel.RESTORE(s = false, e = e.message)
        }
    }
}