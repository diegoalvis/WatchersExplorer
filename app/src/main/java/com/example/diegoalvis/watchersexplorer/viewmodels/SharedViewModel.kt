package com.example.diegoalvis.watchersexplorer.viewmodels

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoalvis.android.newsapp.api.ApiClient
import com.example.diegoalvis.watchersexplorer.models.Repo
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SharedViewModel : ViewModel() {

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    private val apiInterface = ApiClient.getInterface()
    private var lastKeyWordSearched: String? = null

    val repos = MutableLiveData<List<Repo>>()
    val selected = MutableLiveData<Repo>()


    fun select(item: Repo) {
        selected.value = item
    }

    @SuppressLint("CheckResult")
    fun searchRepositories(keyWord: String = "tetris") {
        if (keyWord != lastKeyWordSearched) {
            repos.value = arrayListOf()
        }

        isLoading.set(true)
        apiInterface
            .searchRepos(keyWord)
            .applyUISchedulers()
            .doOnTerminate { isLoading.set(false) }
            .subscribe { repos.value = it.items }
    }
}

private fun <T> Flowable<T>.applyUISchedulers()
        = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

