package com.example.playground.di

import com.example.playground.data.db.AppDatabase
import com.example.playground.repository.MusicRepository
import com.example.playground.repository.MusicRepositoryImp
import com.example.playground.ui.chat.ChatViewModel
import com.example.playground.ui.home.HomeViewModel
import com.example.playground.repository.MainRepositoryMock
import com.example.playground.ui.instrument.InstrumentViewModel
import com.example.playground.ui.main.MainViewModel
import com.example.playground.ui.manage.ManageMusicViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory {
        MainRepositoryMock()
    }

    viewModel {
        MainViewModel(
            musicRepository = get()
        )
    }
    viewModel { HomeViewModel() }
    viewModel { ChatViewModel() }
    viewModel { InstrumentViewModel() }
    viewModel { ManageMusicViewModel() }

    factory { AppDatabase.getInstance(androidApplication()).musicDAO }
    factory { AppDatabase.getInstance(androidApplication()).albumDAO }

    factory<MusicRepository> {
        MusicRepositoryImp(
            musicDAO = get()
        )
    }

}