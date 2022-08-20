package com.gorkemersizer.countries.util

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.gorkemersizer.countries.R

fun ImageView.downloadFromUrl(url: String?) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@downloadFromUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .data(url)
        .target(this)
        .error(R.drawable.ic_error)
        .build()

    imageLoader.enqueue(request)
}