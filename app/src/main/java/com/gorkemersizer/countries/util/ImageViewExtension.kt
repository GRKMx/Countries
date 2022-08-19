package com.gorkemersizer.countries.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gorkemersizer.countries.R
import com.squareup.picasso.Picasso

fun ImageView.downloadFromUrl(url: String?) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@downloadFromUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .data(url)
        .target(this)
        .placeholder(R.drawable.ic_image)
        .error(R.drawable.ic_error)
        .build()

    imageLoader.enqueue(request)
}