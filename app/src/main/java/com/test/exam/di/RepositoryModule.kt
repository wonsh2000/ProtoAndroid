package com.test.yogiyotest.di

import com.test.exam.data.api.KakaoApi
import com.test.exam.data.repository.SearchBookRepository
import dagger.Provides
import javax.inject.Singleton


@Singleton
class RepositoryModule {

    @Provides
    fun provideSearchBookRepository(api: KakaoApi): SearchBookRepository {
        return SearchBookRepository(api)
    }
}
