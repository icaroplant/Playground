package com.example.playground.ui.manage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.data.db.ResponseModel
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SaveMusicViewModel(
    val musicRepository: MusicRepository
) : ViewModel() {

    private val _saveResponse = MutableLiveData<ResponseModel>()
    private val _deleteResponse = MutableLiveData<ResponseModel>()

    val saveReponse : MutableLiveData<ResponseModel> get() = _saveResponse
    val deleteReponse : MutableLiveData<ResponseModel> get() = _deleteResponse

    companion object{
        private val TAG = SaveMusicViewModel::class.java.simpleName
    }

    fun saveMusic(name: String, artist: String?) = viewModelScope.launch {
        try {
            val id = musicRepository.insertMusic(name, artist)
            if(id > 0){
                Log.i(TAG, "SAVE COM SUCESSO: $id")
                _saveResponse.value = ResponseModel(true, id.toString())
            } else{
                Log.i(TAG, "ERRO NO SAVE: $id")
                _saveResponse.value = ResponseModel(false, "ERRO NO INSERT: $id")
            }
        } catch (e: Exception){
            Log.e(TAG, "ERRO NO SAVE: ${e.message}")
            _saveResponse.value = ResponseModel(false, e.message)
        }
    }

    fun updateMusic(id: Long, name: String, artist: String?) = viewModelScope.launch {
        try {
            musicRepository.updateMusic(id, name, artist)
            _saveResponse.value = ResponseModel(true, id.toString())
        } catch (e: Exception){
            Log.e(TAG, "ERRO NO UPDATE: ${e.message}")
            _saveResponse.value = ResponseModel(false, e.message)
        }
    }

    fun deleteMusic(id: Long?) = viewModelScope.launch {
        try {
            musicRepository.deleteMusic(id!!)
            _deleteResponse.value = ResponseModel(true, id.toString())
        } catch (e: Exception){
            Log.e(TAG, "ERRO NO DELETE: ${e.message}")
            _deleteResponse.value = ResponseModel(false, e.message)
        }
    }
}

