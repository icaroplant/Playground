package com.example.playground.di

import com.example.playground.ui.chat.ChatViewModel
import com.example.playground.ui.home.HomeViewModel
import com.example.playground.ui.main.MainRepository
import com.example.playground.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single {
        MainRepository()
    }

    viewModel { MainViewModel(repository = get()) }
    viewModel { HomeViewModel() }
    viewModel { ChatViewModel() }

}