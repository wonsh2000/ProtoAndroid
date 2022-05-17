package com.test.exam.data.model

/*
*   책 검색 > 책 내용 model
*
* */
data class Document(
    var title: String,          //도서 제목
    var contents: String,       //도서 소개
    var url: String,            //도서 상세 URL
    var isbn: String,           //ISBN10(10자리) 또는 ISBN13(13자리) 형식의 국제 표준 도서번호(International Standard Book Number)
                                //ISBN10 또는 ISBN13 중 하나 이상 포함
                                //두 값이 모두 제공될 경우 공백(' ')으로 구분
    var datetime: String,       //도서 출판 날짜 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    var authors: List<String>,  //도서 저자 리스트
    var publisher: String,      //도서 출판사
    var translators: List<String>,    //도서 번역자 리스트
    var price: Int,             //도서 정가
    var salePrice: Int,        //도서 판매가
    var thumbnail: String,      //도서 표지 미리보기 URL
    var status: String,          //도서 판매 상태 정보 (정상, 품절, 절판 등) 상황에 따라 변동 가능성이 있으므로 문자열 처리 지양, 단순 노출 요소로 활용 권장

    var isFavorite: Boolean = false
) {



    fun getPriceStr(): String {
        return price.toString() + "원"
    }

    fun getdateStr(): String {
        return datetime.substring(0, 10)
    }
}
