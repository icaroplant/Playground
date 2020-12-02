package com.example.playground.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.playground.R
import com.example.playground.extensions.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.save_music_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveMusicFragment : Fragment() {

    private val viewModel: SaveMusicViewModel by viewModel()

    private var musicId : Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_music_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.run {
            musicId = this.getLong("musicId", -1).takeIf { it > -1 }?.also {
                activity?.myToolbar?.title = "Atualizar Música"
                btSave.text = "Atualizar"
                etMusicName.setText(getString("musicName", null))
                etMusicArtist.setText(getString("musicArtist", null))
            }
        }

        observe(viewModel.saveReponse){response ->
            if(response.success){
                makeSnackBarWithAction("Salvo com sucesso!", "Desfazer") { view ->
                    viewModel.deleteMusic(response.msg?.toLong())
                }
                clearFields()
                forceHideKeyboard()
            } else{
                response.msg?.let {
                    makeSnackBar(it)
                }
            }
        }

        observe(viewModel.deleteReponse){response ->
            if(!response.success){
                response.msg?.let{
                    makeSnackBar(it)
                }
            }
        }

        btSave.setOnClickListener{
            val name = etMusicName.text.toString().trim()
            val artist = etMusicArtist.text.toString().trim()
            if(musicId != null){
                viewModel.updateMusic(musicId!!, name, artist)
            } else{
                viewModel.saveMusic(name, artist)
            }
        }
    }

    private fun clearFields(){
        etMusicName.text.clear()
        etMusicArtist.text.clear()
    }

}