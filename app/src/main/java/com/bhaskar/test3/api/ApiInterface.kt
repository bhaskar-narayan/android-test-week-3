package com.bhaskar.test3.api

import com.bhaskar.test3.constants.ApiConstant.API_KEY
import com.bhaskar.test3.constants.ApiConstant.END_URL
import com.bhaskar.test3.models.News
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET (END_URL + API_KEY)
    suspend fun getNews(): Response<News>
}