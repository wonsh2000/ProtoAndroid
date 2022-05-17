package com.test.exam

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KakaoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidLogger(Level.ERROR)
//            androidContext(this@kakaoApplication)
//            modules(listOf(networkModule, repositoryModule, viewModelModule))
//        }
    }
}