package com.example.diegoalvis.watchersexplorer.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diegoalvis.android.newsapp.api.ApiClient
import com.example.diegoalvis.watchersexplorer.models.Owner
import com.example.diegoalvis.watchersexplorer.models.Repo
import com.example.diegoalvis.watchersexplorer.utils.applyUISchedulers

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    private val apiInterface = ApiClient.getInterface()
    private var lastKeyWordSearched: String? = null

    val repos = MutableLiveData<List<Repo>>()
    val watchers = MutableLiveData<List<Owner>>()
    val selected = MutableLiveData<Repo>()

    fun select(item: Repo) {
        selected.value = item
    }

    @SuppressLint("CheckResult")
    fun searchRepositories(keyWord: String) {
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

    fun getWatchers() {
        selected.value?.let {
            isLoading.set(true)
            apiInterface
                .getWatchers(it.owner.login, it.url)
                .applyUISchedulers()
                .doOnTerminate { isLoading.set(false) }
                .subscribe { watchers.value = it }
        }
    }
}

