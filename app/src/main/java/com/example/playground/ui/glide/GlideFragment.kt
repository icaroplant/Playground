package com.example.playground.ui.glide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.playground.R
import kotlinx.android.synthetic.main.glide_fragment.*

class GlideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.glide_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(requireContext())
            .load("https://static.bancointer.com.br/gerentes/samara.goncalves@bancointer.com.br.jpg")
            .circleCrop()
            .into(ivPhotoGlide)
    }
}