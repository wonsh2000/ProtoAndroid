package com.test.exam.data.model

data class SearchBook(
    var meta: Meta,                     //메타 정보
    var documents: List<Document>       //도서 정보들
) {

}
