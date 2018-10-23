package com.example.diegoalvis.watchersexplorer.models

data class Repo(
    val name: String,
    val url: String,
    val owner: Owner,
    val description: String,
    val language: String,
    val watchers: Int
)