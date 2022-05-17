package com.test.exam.ui.main

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.exam.R
import com.test.exam.common.ext.dpToPx
import com.test.exam.common.ui.fragment.BaseDataBindingFragment
import com.test.exam.common.ui.view.recyclerview.MarginItemDecoration
import com.test.exam.databinding.SearchFragmentBinding
import javax.inject.Inject


class SearchListFragment : BaseDataBindingFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchListFragment()
    }

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun SearchFragmentBinding.onBind() {
        vm = searchViewModel

        RVList.apply {
            adapter = SearchBookAdaptor(
                onClickItem = { selectecDocument ->
                    searchViewModel.onClickItem(selectecDocument)
                }
            ).apply {
                setHasStableIds(true)

            }
            addItemDecoration(MarginItemDecoration(2F.dpToPx(context)))
        }

        RVList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount
                if (lastVisibleItemPosition + 1 == itemTotalCount) {
                    //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                    searchViewModel.onloadMore()
                }
            }
        })
    }

    override fun recieveEvent(type: Any, data: Any?) {
        when(type) {
            SearchViewModel.MAINEVENT.HIDE_KEYBOARD -> keyHide()
        }
    }


    fun keyHide() {
        val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(binding.ETSearch.windowToken, 0)
    }
}