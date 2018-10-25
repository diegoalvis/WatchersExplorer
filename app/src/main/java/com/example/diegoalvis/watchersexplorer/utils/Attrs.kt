package com.example.diegoalvis.watchersexplorer.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

@BindingAdapter("imgUrl")
fun setImageUrl(img: ImageView, url: String){
    Picasso.get()
        .load(Uri.parse(url))
        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
        .into(img)
}