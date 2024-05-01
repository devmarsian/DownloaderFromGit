package com.downloading.githubfetch

import android.app.Application
import com.downloading.githubfetch.di.databaseModule
import com.downloading.githubfetch.di.networkModule
import com.downloading.githubfetch.di.serviceModule
import com.downloading.githubfetch.di.useCaseModule
import com.downloading.githubfetch.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppClass)
            modules(databaseModule, viewModelModule, serviceModule, networkModule, useCaseModule)
        }
    }
}