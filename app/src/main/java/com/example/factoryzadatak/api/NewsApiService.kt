package com.example.factoryzadatak.api

import com.example.factoryzadatak.data.model.NewsResponse
import retrofit2.http.GET

interface NewsApiService {

    companion object{
        const val BASE_URL = "https://newsapi.org/v1/"
    }

    @GET("articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398")
    suspend fun getNews() : NewsResponse
}