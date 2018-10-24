package com.example.diegoalvis.watchersexplorer.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun setImageUrl(img: ImageView, url: String){
    Glide.with(img.context)
            .load(Uri.parse(url))
            .into(img)
}