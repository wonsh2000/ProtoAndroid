package com.test.exam.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {


    //todo : preference도 구현해 보자
//    @Singleton
//    @Provides
//    fun provideMyKeyStore(@ApplicationContext application: Context): SecurePreference.Crypto {
//        return MyKeyStore(application)
//    }

}