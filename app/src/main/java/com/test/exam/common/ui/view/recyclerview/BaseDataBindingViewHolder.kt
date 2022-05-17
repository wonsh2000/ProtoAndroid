package com.test.exam.common.ui.view.recyclerview

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup

abstract class BaseDataBindingViewHolder<T : Any, B : ViewDataBinding> : BaseViewHolder<T> {

    private val binding: B

    constructor(layoutId: Int, parent: ViewGroup) : super(layoutId, parent) {
        this.binding = DataBindingUtil.bind(itemView)
                ?: throw IllegalStateException("databinding error")
    }

    constructor(view: View) : super(view) {
        this.binding = DataBindingUtil.bind(itemView)
                ?: throw IllegalStateException("databinding error")
    }

    constructor(binding: B) : super(binding.root) {
        this.binding = binding
    }

    protected abstract fun B.onDataBind(data: T)

    override fun onCreated() {
        super.onCreated()

        binding.apply { onBind() }
    }

    override fun onDataBind(data: T) {
        binding.apply {
            onDataBind(data)
        }
    }

    protected open fun B.onBind() {}

    fun getBinding() : B = binding
}