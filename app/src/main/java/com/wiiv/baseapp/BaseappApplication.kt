package com.wiiv.baseapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseappApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}