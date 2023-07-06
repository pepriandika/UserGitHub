package com.firstproject.usergithub.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

// Ekstensi fungsi untuk memuat gambar dari URL ke ImageView menggunakan Glide
fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}