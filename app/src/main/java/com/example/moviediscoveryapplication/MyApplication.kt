package com.example.moviediscoveryapplication

import android.app.Application
import com.example.moviediscoveryapplication.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(homeModule)
        }
    }
}