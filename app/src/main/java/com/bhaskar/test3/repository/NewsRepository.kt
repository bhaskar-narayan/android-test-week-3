package com.bhaskar.test3.repository

import androidx.lifecycle.MutableLiveData
import com.bhaskar.test3.api.ApiClient
import com.bhaskar.test3.models.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsRepository {
    private var newsData = MutableLiveData<News>()

    fun callApi() {
        val apiClient = ApiClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiClient.getNews()
            if (response.isSuccessful)
                newsData.postValue(response.body())
        }
    }

    fun getNews(): MutableLiveData<News> {
        return newsData
    }
}