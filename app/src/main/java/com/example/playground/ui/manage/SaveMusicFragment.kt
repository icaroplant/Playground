package com.example.playground.ui.manage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.playground.R
import com.example.playground.extensions.observe
import kotlinx.android.synthetic.main.save_music_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveMusicFragment : Fragment() {

    private val viewModel: SaveMusicViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_music_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.saveReponse){saveResponse ->
            if(saveResponse){
                Toast.makeText(requireContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else{
                Toast.makeText(requireContext(), "Ocorreu em erro!", Toast.LENGTH_SHORT).show()
            }
        }

        btSave.setOnClickListener{
            viewModel.saveMusic(etMusicName.text.toString().trim(), etMusicArtist.text.toString().trim())
        }
    }

}