package com.example.playground.ui.home

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.HomeFragmentBinding
import com.example.playground.extensions.navigateWithAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CoreFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
    private val args: HomeFragmentArgs by navArgs()

    override fun setupViews() {
        binding.tvTitle.text = args.title
        binding.tvNumber.text = args.list?.split("\n")?.size?.toString()
        binding.tvList.text = args.list

        binding.btChat.setOnClickListener() {
            findNavController().navigateWithAnimations(R.id.action_homeFragment_to_chatFragment)
        }
    }
}