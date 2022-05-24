package com.wiiv.baseapp.ui.main.view

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiiv.baseapp.R
import com.wiiv.baseapp.common.ext.dpToPx
import com.wiiv.baseapp.common.ui.fragment.BaseDataBindingFragment
import com.wiiv.baseapp.common.ui.view.recyclerview.MarginItemDecoration
import com.wiiv.baseapp.databinding.SearchFragmentBinding
import com.wiiv.baseapp.ui.main.adapter.SearchBookAdaptor
import com.wiiv.baseapp.ui.main.viewmodel.SearchViewModel


class SearchListFragment : BaseDataBindingFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchListFragment()
    }

    private val searchViewModel: SearchViewModel by activityViewModels()

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