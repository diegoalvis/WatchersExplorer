package com.example.diegoalvis.watchersexplorer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.api.SearchResponse
import com.example.diegoalvis.watchersexplorer.models.Repo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

class SharedViewModel : ViewModel() {
//    var isLoading: ObservableBoolean = ObservableBoolean(false)

    private val apiInterface = ApiClient.getInterface()

    val repos = MutableLiveData<List<Repo>>()
    val selected = MutableLiveData<Repo>()


    fun select(item: Repo) {
        selected.value = item
    }

    fun searchRepositories(keyWords: String): Flowable<SearchResponse>? {
//        isLoading.set(true)
        return apiInterface
            .searchRepos(keyWords)
//            .doOnTerminate { isLoading.set(false) }
            .onBackpressureBuffer()
            .observeOn(AndroidSchedulers.mainThread())
    }
}
