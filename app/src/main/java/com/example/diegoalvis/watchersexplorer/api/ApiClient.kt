package com.diegoalvis.android.newsapp.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object create {

        private val BASE_URL: String = "http://api.nytimes.com/svc/topstories/v2/"
        private var retrofit: Retrofit? = null

        @Synchronized private fun createInstance() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .build()
            }
        }


        fun getInstance(): ApiInterface {
            if (retrofit == null)
                createInstance()
            return retrofit!!.create(ApiInterface::class.java)
        }
    }
}