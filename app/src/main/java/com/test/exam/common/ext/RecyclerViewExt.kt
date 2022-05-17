package com.test.exam.common.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.exam.common.ui.view.recyclerview.BaseRecyclerViewAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    if (list == null) {
        return
    }

    when {
        adapter as? BaseRecyclerViewAdapter<Any> != null -> {
            (adapter as BaseRecyclerViewAdapter<Any>).run {
                replaceAll(list)
                notifyDataSetChanged()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAllSilently")
fun RecyclerView.replaceAllSilently(list: List<Any>?) {
    if (list == null) {
        return
    }

    when {
        adapter as? BaseRecyclerViewAdapter<Any> != null -> {
            (adapter as BaseRecyclerViewAdapter<Any>).replaceAll(list)
        }
    }
}