package com.example.diegoalvis.watchersexplorer.models

import com.google.gson.annotations.SerializedName

data class Owner(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") var htmlUrl: String
)