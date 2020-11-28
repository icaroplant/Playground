package com.example.playground.ui.manage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.repository.MusicRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SaveMusicViewModel(
    val musicRepository: MusicRepository
) : ViewModel() {

    private val _saveResponse = MutableLiveData<Boolean>()

    val saveReponse : MutableLiveData<Boolean> get() = _saveResponse

    companion object{
        private val TAG = SaveMusicViewModel::class.java.simpleName
    }

    fun saveMusic(name: String, artist: String?) = viewModelScope.launch {
        try {
            val id = musicRepository.insertMusic(name, artist)
            if(id > 0){
                Log.i(TAG, "SAVE COM SUCESSO: $id")
                _saveResponse.value = true
            } else{
                Log.i(TAG, "ERRO NO SAVE: $id")
                _saveResponse.value = false
            }
        } catch (e: Exception){
            Log.e(TAG, "ERRO NO SAVE: ${e.message}")
        }
    }
}