package com.test.exam.data.api

import com.test.exam.data.model.SearchBook
import io.reactivex.Single
import retrofit2.http.*

interface KakaoApi {

    //책 리스트 불러오기
    @GET("/v3/search/book")
    fun getSearchBook(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("target") target: String?
    ): Single<SearchBook>
}