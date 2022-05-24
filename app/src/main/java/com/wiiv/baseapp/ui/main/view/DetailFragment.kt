package com.wiiv.baseapp.ui.main.view

import androidx.fragment.app.activityViewModels
import com.wiiv.baseapp.R
import com.wiiv.baseapp.common.ui.fragment.BaseDataBindingFragment
import com.wiiv.baseapp.databinding.DetailFragmentBinding
import com.wiiv.baseapp.ui.main.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseDataBindingFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun DetailFragmentBinding.onBind() {
        vm = searchViewModel
    }

}