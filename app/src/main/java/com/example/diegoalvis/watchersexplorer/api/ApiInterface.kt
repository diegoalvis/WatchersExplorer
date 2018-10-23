package com.diegoalvis.android.newsapp.api

import com.example.diegoalvis.watchersexplorer.models.Owner
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/search/repositories")
    fun searchRepos(@Query("q") keyWords: String, @Query("page") page: Int? = null): Flowable<SearchResponse>

    @GET("/repos/{owner}/{repo}/subscribers")
    fun getWatchers(@Path("owner") owner: String, @Path("repo") repo: String, @Query("page") page: Int? = null): Flowable<ArrayList<Owner>>
}