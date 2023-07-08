package com.firstproject.usergithub.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

// Ekstensi fungsi untuk memuat gambar dari URL ke ImageView menggunakan Glide
fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .transform(CircleCrop())
        .into(this)

}