package com.wiiv.baseapp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wiiv.baseapp.common.ui.viewmodel.BaseViewModel
import com.wiiv.baseapp.common.util.disposeOnDestroy
import com.wiiv.baseapp.data.model.Document
import com.wiiv.baseapp.data.repository.SearchBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBookRepository: SearchBookRepository
) : BaseViewModel(){


    //화면 이벤트
    enum class MAINEVENT {
        SELECT_ITEM,        //리스트  아이템 선택시(디테일화면이동)
        ONCLICK_BACK,       //백버튼 클릭 시
        HIDE_KEYBOARD,      //키보드 숨기기
        SHOW_PROGRESS,      //로딩바 보이기
        HIDE_PROGRESS,      //로딩바 숨기기
        SHOW_NO_MORE_TOAST, //로딩 완료시 토스트
        SHOW_ERROR_TOAST    //에러 토스트
    }

    companion object {
        //카카오 api token
        var token = "KakaoAK 74cb7d92c8f0755c0ebbf9e31bfc09b6"
    }

    var pagingIndex = 1
    var isEnd = false
    // 검색어
    var liveQueryStr = MutableLiveData<String>()

    // 불러온 북 아이템
    var liveSearchBookList = MutableLiveData<MutableList<Document>>()

    // 선택되어진 북 아이템
    var liveSeletedBook = MutableLiveData<Document>()


    /*
    *   [UI] 리스트에서 아이템을 클릭 했을 경우 > 디테일 화면으로 이동
    *
    * */
    fun onClickItem(data: Document) {
        liveSeletedBook.postValue(data)
        //아이템 클릿 시, detail 화면 열기
        sendEventRx(MAINEVENT.SELECT_ITEM)
    }

    /*
    *   리스트 스크롤 시, 추가 아이템 불러오기
    *
    * */
    fun onloadMore() {
        // 더이상 불러올 수 없을 경우, 토스트 알림
        if(isEnd) {
            sendEventRx(MAINEVENT.SHOW_NO_MORE_TOAST)
            return
        }

        liveQueryStr.value?.let { query ->
            searchBookRepository.getBookData(token, query, page = pagingIndex)
                .doOnSubscribe {
                    sendEventRx(MAINEVENT.SHOW_PROGRESS)
                }
                .doFinally {
                    sendEventRx(MAINEVENT.HIDE_PROGRESS)
                }
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe ({ addBook ->

                    isEnd = addBook.meta.is_end
                    pagingIndex++
                    liveSearchBookList.value?.apply {
                        addAll(addBook.documents)
                    }.let {
                        liveSearchBookList.postValue(it)
                    }
                }, {
                    sendEventRx(MAINEVENT.SHOW_ERROR_TOAST, it.message)
                })
                .disposeOnDestroy(this)
        }
    }


    /*
    *   [UI] 검색 버튼을 클릭했을 경우 동작 메소드
    *   - query 값을 가져와 api 호출한다.
    * */
    fun onClickSearch() {

        //첫 호출 시 페이지 인덱스 초기화
        pagingIndex = 1
        liveSearchBookList.value?.clear()
        isEnd = false

        liveQueryStr.value?.let { query ->
            searchBookRepository.getBookData(token, query, page = pagingIndex)
                .doOnSubscribe {
                    sendEventRx(MAINEVENT.HIDE_KEYBOARD)
                    sendEventRx(MAINEVENT.SHOW_PROGRESS)
                }
                .doFinally {
                    sendEventRx(MAINEVENT.HIDE_PROGRESS)
                }
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe ({ book ->
                    isEnd = book.meta.is_end
                    liveSearchBookList.postValue(book.documents.toMutableList())
                    pagingIndex++
                }, {
                    sendEventRx(MAINEVENT.SHOW_ERROR_TOAST, it.message)
                }).disposeOnDestroy(this)
        }
    }

    /*
    *   [UI] 디테일 화면에서 뒤로 가기 버튼 클릭 시
    * */
    fun onClickBack() = sendEventRx(MAINEVENT.ONCLICK_BACK)

    /*
    *   [UI] 디테일화면에서 즐겨찾기 아이콘 클릭했을 경우
    *   - 디테일화면에도 적용 되도록 한다.
    * */
    fun onClickFavorite(isFavorite: Boolean) {
        liveSeletedBook.value?.let { selectedBook ->
            liveSeletedBook.postValue(selectedBook.copy(isFavorite = !isFavorite))
            liveSearchBookList.value?.map {
                if(it.isbn == selectedBook.isbn) it.isFavorite = !isFavorite
            }
        }
    }
}