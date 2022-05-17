package com.test.exam.ui.main

import com.test.exam.R
import com.test.exam.common.ui.fragment.BaseDataBindingFragment
import com.test.exam.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseDataBindingFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    companion object {
        fun newInstance() = DetailFragment()
    }

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun DetailFragmentBinding.onBind() {
        vm = searchViewModel
    }

}