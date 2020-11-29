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

    val saveReponse : MutableLiveData<ResponseModel> get() = _saveResponse

    companion object{
        private val TAG = SaveMusicViewModel::class.java.simpleName
    }

    fun saveMusic(name: String, artist: String?) = viewModelScope.launch {
        try {
            val id = musicRepository.insertMusic(name, artist)
            if(id > 0){
                Log.i(TAG, "SAVE COM SUCESSO: $id")
                _saveResponse.value = ResponseModel(true)
            } else{
                Log.i(TAG, "ERRO NO SAVE: $id")
                _saveResponse.value = ResponseModel(true, "ERRO NO INSERT: $id")
            }
        } catch (e: Exception){
            Log.e(TAG, "ERRO NO SAVE: ${e.message}")
            _saveResponse.value = ResponseModel(false, e.message)
        }
    }
}