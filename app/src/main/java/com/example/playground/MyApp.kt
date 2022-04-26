package com.example.playground

import android.app.Application
import android.content.Context
import com.example.playground.data.db.AppDatabase
import com.example.playground.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApp)

            modules(mainModule)
        }
    }

}