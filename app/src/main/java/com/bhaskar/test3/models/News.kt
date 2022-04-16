package com.bhaskar.test3.models

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)