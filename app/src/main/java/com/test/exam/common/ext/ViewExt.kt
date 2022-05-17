package com.test.exam.common.ext

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["activated"])
fun View.setActivated(isActivated: Boolean) {
    this.isActivated = isActivated
}