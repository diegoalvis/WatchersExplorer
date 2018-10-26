package com.example.diegoalvis.watchersexplorer.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.api.SearchResponse
import com.example.diegoalvis.watchersexplorer.models.Owner
import com.example.diegoalvis.watchersexplorer.models.Repo
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

open class SharedViewModel : ViewModel() {

    private var pageRepoCounter = 1
    private var pageWatcherCounter = 1
    private var lastKeyWordSearched: String = ""
    private var lastSort: String? = null
    private var lastOrder: String? = null

    var selected = MutableLiveData<Repo>()
    val watchers = MutableLiveData<MutableList<Owner>>()
    var apiInterface = ApiClient.getInterface()
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    val repos = MutableLiveData<MutableList<Repo>>()

    fun select(item: Repo) {
        selected.value = item
    }

    // fetch watchers
    fun getWatchers(page: Int = 1): Flowable<MutableList<Owner>>? {
        if (page == 1) {
            pageWatcherCounter = 1
        }
        return selected.value?.let {
            isLoading.set(true)
            apiInterface
                .getWatchers(it.owner.login, it.name, page)
                .doOnError { isLoading.set(false) }
                .doOnComplete { isLoading.set(false) }
        }
    }

    fun getMoreWatchers(): Flowable<MutableList<Owner>>? {
        pageWatcherCounter += 1
        return getWatchers(pageWatcherCounter)
    }

    // fetch repositories
    fun searchRepositories(keyWord: String, sort: String? = null, order: String? = null, page: Int = 1): Flowable<SearchResponse> {
        lastKeyWordSearched = keyWord
        lastSort = sort
        lastOrder = order
        if (page == 1) {
            pageRepoCounter = 1
        }

        isLoading.set(true)
        return apiInterface
            .searchRepos(keyWord, sort, order, page)
            .throttleFirst(1, TimeUnit.SECONDS)
            .doOnError { isLoading.set(false) }
            .doOnComplete { isLoading.set(false) }
    }

    fun getMoreRepos(): Flowable<SearchResponse> {
        pageRepoCounter += 1
        return searchRepositories(lastKeyWordSearched, lastSort, lastOrder, pageRepoCounter)
    }
}