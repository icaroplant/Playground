package com.example.playground.di

import com.example.playground.data.db.AppDatabase
import com.example.playground.repository.MusicRepository
import com.example.playground.repository.MusicRepositoryImp
import com.example.playground.ui.chat.ChatViewModel
import com.example.playground.ui.home.HomeViewModel
import com.example.playground.repository.MainRepositoryMock
import com.example.playground.ui.main.MainViewModel
import com.example.playground.ui.manage.SaveMusicViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single {
        MainRepositoryMock()
    }

    viewModel { MainViewModel(
        musicRepository = get()
    ) }
    viewModel { HomeViewModel() }
    viewModel { ChatViewModel() }
    viewModel { SaveMusicViewModel(musicRepository = get()) }

    single { AppDatabase.getInstance(androidApplication()).musicDAO }
    single { AppDatabase.getInstance(androidApplication()).albumDAO }

    single<MusicRepository>{
        MusicRepositoryImp(
            musicDAO = get()
        )
    }

}