package com.test.exam.common.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter(value = ["loadUrl", "errorUrl"], requireAll = false)
fun ImageView.loadUrl(url: String?, errorUrl: String?) {

    if (url.isNullOrBlank()) {
        return
    }

    Glide.with(this).clear(this)




    url.let {
        val urlRequestBuilder = Glide.with(this).load(it)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        errorUrl?.let { errUrl ->
            val errUrlRequestBuilder = Glide.with(this).load(errUrl)
            urlRequestBuilder
                .dontAnimate()
                .thumbnail(errUrlRequestBuilder)
                .error(errUrlRequestBuilder)
        }

        urlRequestBuilder.into(this)
    }
}