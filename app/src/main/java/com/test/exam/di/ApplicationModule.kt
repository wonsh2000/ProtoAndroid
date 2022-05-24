package com.test.exam.di

import com.test.exam.BuildConfig
import com.test.exam.data.api.KakaoApi
import com.test.exam.util.GsonFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule  {

    private val TIME_OUT = 15L

    var loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpclient(): okhttp3.OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
        }.build()
    }


    //okhttp
    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoApi(adaptor: Retrofit) : KakaoApi {
        return adaptor.create(KakaoApi::class.java)
    }
}