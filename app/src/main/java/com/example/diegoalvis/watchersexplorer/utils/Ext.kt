package com.example.diegoalvis.watchersexplorer.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// ViewGroup
fun ViewGroup.inflate(layout:Int) = LayoutInflater.from(context).inflate(layout, this, false)

// RX
fun <T> Flowable<T>.applyUISchedulers()
        = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

// RX
fun <T> Observable<T>.applyUISchedulers()
        = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

// Fragment
fun FragmentManager.replace(container: Int, fragment: Fragment, tag: String) {
    this.beginTransaction().replace(container, fragment, tag).commitAllowingStateLoss()
}

