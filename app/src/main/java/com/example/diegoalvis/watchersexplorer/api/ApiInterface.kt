package com.diegoalvis.android.newsapp.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.acl.Owner

interface ApiInterface {

    @GET("/search/repositories")
    fun searchRepos(@Query("q") keyWords: String, @Query("page") page: Int? = null): Flowable<SearchResponse>

    @GET("/repos/:owner/:repo/subscribers")
    fun getWatchers(@Query("page") page: Int? = null): Flowable<List<Owner>>
}