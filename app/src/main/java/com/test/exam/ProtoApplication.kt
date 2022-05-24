package com.test.exam

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProtoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}