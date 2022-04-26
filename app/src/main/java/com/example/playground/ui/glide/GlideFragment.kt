package com.example.playground.ui.glide

import com.bumptech.glide.Glide
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.GlideFragmentBinding

class GlideFragment : CoreFragment<GlideFragmentBinding>(GlideFragmentBinding::inflate) {

    override fun setupViews() {
        Glide.with(requireContext())
            .load("https://static.bancointer.com.br/gerentes/samara.goncalves@bancointer.com.br.jpg")
            .circleCrop()
            .into(binding.ivPhotoGlide)
    }
}