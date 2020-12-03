package com.example.playground.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.data.db.ResponseModel
import com.example.playground.extensions.forceHideKeyboard
import com.example.playground.extensions.makeSnackBar
import com.example.playground.extensions.makeSnackBarWithAction
import com.example.playground.extensions.observe
import com.example.playground.utils.BaseBottomSheet
import kotlinx.android.synthetic.main.save_music_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageMusicModalBottomSheet : BaseBottomSheet() {

    private val viewModel: ManageMusicViewModel by viewModel()

    private var entity : MusicModel? = null

    companion object {

        private const val MUSIC_KEY = "MUSIC_KEY"

        fun newInstance(model: MusicModel): ManageMusicModalBottomSheet {
            return ManageMusicModalBottomSheet().apply {
                this.arguments = Bundle().apply {
                    putParcelable(MUSIC_KEY, model)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manage_music_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entity = arguments?.getParcelable<MusicModel>(MUSIC_KEY)?.also {
            btSave.text = "Atualizar"
            etMusicName.setText(it.name)
            etMusicArtist.setText(it.artist)
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
            entity?.let {
                viewModel.updateMusic(it.id, name, artist)
            } ?:
                viewModel.saveMusic(name, artist)

        }
    }

    private fun clearFields(){
        etMusicName.text.clear()
        etMusicArtist.text.clear()
    }

}