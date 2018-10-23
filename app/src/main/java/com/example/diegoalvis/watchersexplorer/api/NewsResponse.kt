package com.diegoalvis.android.newsapp.api

import com.diegoalvis.android.newsapp.models.Article
import com.google.gson.annotations.SerializedName

class NewsResponse {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("num_results")
    var numResults: Int? = null

    @SerializedName("results")
    val newsList: List<Article> = ArrayList()

}