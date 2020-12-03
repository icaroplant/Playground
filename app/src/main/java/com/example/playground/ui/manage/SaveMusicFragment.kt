package com.example.playground.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.data.db.ResponseModel
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

        observe(viewModel.repositoryReponse){response ->

            if(response.success){
                when (response) {
                    is ResponseModel.INSERT -> {
                        makeSnackBarWithAction("Salvo com sucesso!", "Desfazer") { view ->
                            response.model?.id?.let {id ->
                                viewModel.deleteMusic(id)
                            }
                        }
                        clearFields()
                    }
                    is ResponseModel.UPDATE -> {
                        makeSnackBarWithAction("Atualizado com sucesso!", "Desfazer") { view ->
                            response.model?.let { backup ->
                                viewModel.restoreMusicFromUpdate(backup.id, backup.name, backup.artist)
                            }
                        }
                    }
                    is ResponseModel.RESTORE ->{
                        makeSnackBar("Música restaurada com sucesso!")
                        response.model?.let { backup ->
                            etMusicName.setText(backup.name)
                            etMusicArtist.setText(backup.artist)
                        }
                    }
                    else -> {
                    }
                }
                forceHideKeyboard()
            } else{
                response.error?.let {
                    makeSnackBar("Erro: $it")
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