package com.example.diegoalvis.watchersexplorer.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.api.SearchResponse
import com.example.diegoalvis.watchersexplorer.models.Owner
import com.example.diegoalvis.watchersexplorer.models.Repo
import com.example.diegoalvis.watchersexplorer.utils.applyUISchedulers
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var pageCounter = 1

    private val apiInterface = ApiClient.getInterface()
    private var lastKeyWordSearched: String = ""

    val repos = MutableLiveData<MutableList<Repo>>()
    val watchers = MutableLiveData<MutableList<Owner>>()
    val selected = MutableLiveData<Repo>()

    fun select(item: Repo) {
        selected.value = item
    }

    fun getWatchers() {
        selected.value?.let {
            isLoading.set(true)
            apiInterface
                .getWatchers(it.owner.login, it.name)
                .applyUISchedulers()
                .doOnTerminate { isLoading.set(false) }
                .subscribe({ watchers.value = it }, { it.printStackTrace() })
        }
    }

    // Fetch repositories
    fun searchRepositories(keyWord: String, page: Int = 1): Flowable<SearchResponse> {
        lastKeyWordSearched = keyWord
        if (page == 1) {
            pageCounter  = 1
        }

        isLoading.set(true)
        return apiInterface
            .searchRepos(keyWord, page)
            .throttleFirst(1, TimeUnit.SECONDS)
            .applyUISchedulers()
            .doOnTerminate { isLoading.set(false) }
    }

    fun getMoreRepos(): Flowable<SearchResponse> {
        pageCounter += 1
        return searchRepositories(lastKeyWordSearched, pageCounter)
    }
}