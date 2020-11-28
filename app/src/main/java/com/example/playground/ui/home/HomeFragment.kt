package com.example.playground.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.playground.R
import com.example.playground.extensions.navigateWithAnimations
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = arguments

        if(args != null){
            tv_title.text = args.getString("title", "Erro")
            tv_number.text = args.getInt("number", -1).toString()
            tv_list.text = args.getString("list", "Erro")
        }

        bt_chat.setOnClickListener(){
            findNavController().navigateWithAnimations(R.id.action_homeFragment_to_chatFragment)
        }



    }

}