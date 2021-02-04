package com.example.factoryzadatak.data

import androidx.room.*
import com.example.factoryzadatak.data.model.NewsArticle

@Dao
interface NewsDao{


    @Query("SELECT * FROM news_articles WHERE timePosted > :fiveMinAgo")
    suspend fun getRecentNews(fiveMinAgo: Long): List<NewsArticle>

    @Insert
    suspend fun insertNews(newsArticles: List<NewsArticle>)
}