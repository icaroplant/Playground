package com.example.playground.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playground.R
import com.example.playground.ui.main.MainViewModel
import com.example.playground.utils.BaseBottomSheet
import kotlinx.android.synthetic.main.manage_music_bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageMusicModalBottomSheet(
    private val mainViewModel: MainViewModel
) : BaseBottomSheet() {

    private val viewModel: ManageMusicViewModel by viewModel()

    private var entity : MusicModel? = null

    companion object {

        private const val MUSIC_KEY = "MUSIC_KEY"

        fun newInstance(mainViewModel: MainViewModel, model: MusicModel? = null): ManageMusicModalBottomSheet {
            return ManageMusicModalBottomSheet(mainViewModel).apply {
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

        btSave.setOnClickListener{
            val name = etMusicName.text.toString().trim()
            val artist = etMusicArtist.text.toString().trim()
            if(name.isNotEmpty()){
                entity?.let {
                    mainViewModel.updateMusic(it.id, name, artist)
                } ?:
                mainViewModel.saveMusic(name, artist)
                dismiss()
            } else{
                etMusicName.error = "Campo obrigatório"
            }
        }
    }

    private fun clearFields(){
        etMusicName.text.clear()
        etMusicArtist.text.clear()
    }

}