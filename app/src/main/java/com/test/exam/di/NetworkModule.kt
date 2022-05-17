package com.test.exam.di

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
object NetworkModuleHilt {

    private const val TIME_OUT = 15L

    const val NETWORK_OK_HTTP_CLIENT = "okHttpClient"
    const val NETWORK_RETROFIT_ADAPTER = "retrofitAdapter"
    const val BASE_URL = "https://dapi.kakao.com"

    var loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }



    @Singleton
    @Provides
    @Named(NETWORK_OK_HTTP_CLIENT)
    fun provideOkHttpclient(): okhttp3.OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
        }.build()
    }


    //okhttp
    @Singleton
    @Provides
    @Named(NETWORK_RETROFIT_ADAPTER)
    fun provideRetrofit(@Named(NETWORK_OK_HTTP_CLIENT) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideKakaoApi(@Named(NETWORK_RETROFIT_ADAPTER) adaptor: Retrofit) : KakaoApi {
        return adaptor.create(KakaoApi::class.java)
    }
}