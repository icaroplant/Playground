package com.example.playground.ui.home

import androidx.navigation.fragment.findNavController
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.HomeFragmentBinding
import com.example.playground.extensions.navigateWithAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CoreFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun setupViews() {
        val args = arguments

        if (args != null) {
            binding.tvTitle.text = args.getString("title", "Erro")
            binding.tvNumber.text = args.getInt("number", -1).toString()
            binding.tvList.text = args.getString("list", "Erro")
        }

        binding.btChat.setOnClickListener() {
            findNavController().navigateWithAnimations(R.id.action_homeFragment_to_chatFragment)
        }
    }
}