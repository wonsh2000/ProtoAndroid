package com.wiiv.baseapp.common.ext

import android.content.Context
import android.util.TypedValue

fun Float.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.getResources().displayMetrics).toInt()
}