package com.test.exam.data.model

data class Meta(
    val totalCount: Int,        //검색된 문서 수
    val pageableCount: Int,     //중복된 문서를 제외하고, 처음부터 요청 페이지까지의 노출 가능 문서 수
    val is_end: Boolean         //현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
)
