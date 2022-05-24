package com.test.exam.ui.main.view

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.test.exam.R
import com.test.exam.common.ui.fragment.BaseDataBindingFragment
import com.test.exam.databinding.DetailFragmentBinding
import com.test.exam.ui.main.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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