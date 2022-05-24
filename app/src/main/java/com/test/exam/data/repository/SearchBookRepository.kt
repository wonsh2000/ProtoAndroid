package com.test.exam.data.repository

import com.test.exam.data.api.KakaoApi
import com.test.exam.data.model.SearchBook
import io.reactivex.Single
import javax.inject.Inject

class SearchBookRepository @Inject constructor(
    private val searchBookApi: KakaoApi
) {

    /*
    *   bookdata 가져오는 메소드
    *   @PARAM_query : 검색을 원하는 질의어
    *   @PARAM_sort : 결과 문서 정렬 방식, accuracy(정확도순) 또는 latest(발간일순), 기본값 accuracy
    *   @PARAM_page : 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
    *   @PARAM_size : 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
    *   @PARAM_target : 검색 필드 제한 > 사용 가능한 값: title(제목), isbn (ISBN), publisher(출판사), person(인명)
    *
    * */
    fun getBookData(token: String,
                    query: String,
                    sort: String? = null,
                    page: Int = 1,
                    size: Int = 50,
                    target: String? = null
    ): Single<SearchBook> {
        return searchBookApi.getSearchBook(token, query, sort, page, size, target)
    }
}