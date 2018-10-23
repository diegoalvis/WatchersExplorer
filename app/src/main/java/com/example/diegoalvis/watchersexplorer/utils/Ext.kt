package com.example.diegoalvis.watchersexplorer.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// ViewGroup
fun ViewGroup.inflate(layout:Int) = LayoutInflater.from(context).inflate(layout, this, false)

// RX
fun <T> Flowable<T>.applyUISchedulers()
        = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


