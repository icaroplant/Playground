package com.example.playground.ui.manage

import android.os.Bundle
import com.example.playground.common.CoreBottomSheet
import com.example.playground.databinding.ManageMusicBottomSheetBinding
import com.example.playground.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageMusicModalBottomSheet(
    private val mainViewModel: MainViewModel
) : CoreBottomSheet<ManageMusicBottomSheetBinding>(ManageMusicBottomSheetBinding::inflate) {

    private val viewModel: ManageMusicViewModel by viewModel()

    private var entity: MusicModel? = null

    companion object {

        private const val MUSIC_KEY = "MUSIC_KEY"

        fun newInstance(
            mainViewModel: MainViewModel,
            model: MusicModel? = null
        ): ManageMusicModalBottomSheet {
            return ManageMusicModalBottomSheet(mainViewModel).apply {
                model?.let {
                    arguments = Bundle().apply {
                        putParcelable(MUSIC_KEY, it)
                    }
                }
            }
        }
    }

    override fun setupViews() {
        entity = arguments?.getParcelable<MusicModel>(MUSIC_KEY)?.also {
            binding.btSave.text = "Atualizar"
            binding.etMusicName.setText(it.name)
            binding.etMusicArtist.setText(it.artist)
        }

        binding.btSave.setOnClickListener {
            val name = binding.etMusicName.text.toString().trim()
            val artist = binding.etMusicArtist.text.toString().trim()
            if (name.isNotEmpty()) {
                entity?.let {
                    mainViewModel.updateMusic(it.id, name, artist)
                } ?: mainViewModel.saveMusic(name, artist)
                dismiss()
            } else {
                binding.etMusicName.error = "Campo obrigatório"
            }
        }
    }

    private fun clearFields() {
        binding.etMusicName.text.clear()
        binding.etMusicArtist.text.clear()
    }

}