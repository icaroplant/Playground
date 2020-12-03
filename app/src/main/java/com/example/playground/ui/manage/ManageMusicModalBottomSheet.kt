package com.example.playground.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.data.db.ResponseModel
import com.example.playground.extensions.*
import com.example.playground.utils.BaseBottomSheet
import kotlinx.android.synthetic.main.manage_music_bottom_sheet.*
import kotlinx.android.synthetic.main.save_music_fragment.*
import kotlinx.android.synthetic.main.save_music_fragment.btSave
import kotlinx.android.synthetic.main.save_music_fragment.etMusicArtist
import kotlinx.android.synthetic.main.save_music_fragment.etMusicName
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageMusicModalBottomSheet : BaseBottomSheet() {

    private val viewModel: ManageMusicViewModel by viewModel()

    private var entity : MusicModel? = null

    companion object {

        private const val MUSIC_KEY = "MUSIC_KEY"

        fun newInstance(model: MusicModel? = null): ManageMusicModalBottomSheet {
            return ManageMusicModalBottomSheet().apply {
                model?.let {
                    arguments = Bundle().apply {
                        putParcelable(MUSIC_KEY, it)
                    }
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
                        makeSnackBarWithActionFrom(clDummy,"Salvo com sucesso!", "Desfazer") { view ->
                            response.model?.id?.let {id ->
                                viewModel.deleteMusic(id)
                            }
                        }
                        clearFields()
                    }
                    is ResponseModel.UPDATE -> {
                        makeSnackBarWithActionFrom(clDummy,"Atualizado com sucesso!", "Desfazer") { view ->
                            response.model?.let { backup ->
                                viewModel.restoreMusicFromUpdate(backup.id, backup.name, backup.artist)
                            }
                        }
                    }
                    is ResponseModel.RESTORE ->{
                        makeSnackBarFrom(clDummy,"Música restaurada com sucesso!")
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
                    makeSnackBarFrom(clDummy,"Erro: $it")
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