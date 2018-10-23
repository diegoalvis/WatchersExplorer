package com.diegoalvis.android.newsapp.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("{section}.json")
    fun getNewsList(@Path("section") section: String, @Query("api-key") apiKey: String): Observable<NewsResponse>

}