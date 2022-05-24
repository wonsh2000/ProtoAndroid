package com.wiiv.baseapp.common.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.wiiv.baseapp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

object Loading {
    private var dialog: Dialog? = null

    fun show(context: Context?) {
        dissmiss()

        if (context != null) {
            dialog = Dialog(context, R.style.CustomDialogTheme)
                    .apply {
                        requestWindowFeature(Window.FEATURE_NO_TITLE)
                        setContentView(R.layout.loading_dialog)
                        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setCanceledOnTouchOutside(true)
                        setCancelable(true)
                        show()
                    }
        }
    }

    @SuppressLint("CheckResult")
    fun dissmiss() {
        if (dialog != null) {
            Observable.just(dialog)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it!!.dismiss() }
        }
    }
}