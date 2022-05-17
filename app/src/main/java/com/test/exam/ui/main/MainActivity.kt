package com.test.exam.ui.main

import android.widget.Toast
import androidx.activity.viewModels
import com.test.exam.R
import com.test.exam.common.ui.activity.BaseDataBindingActivity
import com.test.exam.common.util.Loading
import com.test.exam.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseDataBindingActivity<MainActivityBinding>(R.layout.main_activity) {

    enum class TAB {
        LIST,
        DETAIL
    }


    private val searchViewModel: SearchViewModel by viewModels()

    private var mCurrentTab = TAB.LIST

    //private val searchViewModel by viewModel<SearchViewModel>()

    private val searchlistfrgment = SearchListFragment.newInstance()
    private val detailFragment = DetailFragment.newInstance()

    override fun MainActivityBinding.onBind() {
        vm = searchViewModel
        registerRxBus()

        setTab(TAB.LIST)
    }

    override fun recieveEvent(type: Any, data: Any?) {
        when(type) {
            SearchViewModel.MAINEVENT.SELECT_ITEM -> {
                setTab(TAB.DETAIL)
            }
            SearchViewModel.MAINEVENT.ONCLICK_BACK -> {
                setTab(TAB.LIST)
            }
            SearchViewModel.MAINEVENT.SHOW_PROGRESS -> {
                Loading.show(this@MainActivity)
            }
            SearchViewModel.MAINEVENT.HIDE_PROGRESS -> {
                Loading.dissmiss()
            }
            SearchViewModel.MAINEVENT.SHOW_NO_MORE_TOAST -> {
                showToast(getString(R.string.no_more_data))
            }
            SearchViewModel.MAINEVENT.SHOW_ERROR_TOAST -> {
                showToast(data as String)
            }
        }
    }

    override fun onBackPressed() {
        when(mCurrentTab) {
            TAB.LIST -> {
                finish()
            }
            TAB.DETAIL -> {
                setTab(TAB.LIST)
            }
        }
    }

    fun setTab(tab: TAB) {
        mCurrentTab = tab
        when(tab) {
            TAB.LIST -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, searchlistfrgment)
                    .commitNow()
            }
            TAB.DETAIL -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .commitNow()
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}