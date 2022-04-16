package com.bhaskar.test3.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhaskar.test3.models.News
import com.bhaskar.test3.repository.NewsRepository

class MainViewModel : ViewModel() {
    private val repository = NewsRepository()
    fun callApi() {
        repository.callApi()
    }
    fun getNews(): MutableLiveData<News> {
        return repository.getNews()
    }
}