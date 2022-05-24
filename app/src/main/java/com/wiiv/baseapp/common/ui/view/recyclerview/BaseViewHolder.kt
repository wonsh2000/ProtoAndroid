package com.wiiv.baseapp.common.ui.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseViewHolder<T : Any> : androidx.recyclerview.widget.RecyclerView.ViewHolder {

    private lateinit var data: T

    constructor(layoutId: Int, parent: ViewGroup)
            : this(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    constructor(view: View) : super(view)

    protected abstract fun onDataBind(data: T)

    open fun onCreated() {
        setupView(itemView)
    }

    fun bindData(data: T) {
        this.data = data

        onDataBind(data)
    }

    fun getData() = data

    protected open fun setupView(itemView: View) {}
}