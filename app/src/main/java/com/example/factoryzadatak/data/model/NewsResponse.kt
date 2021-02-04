package com.example.factoryzadatak.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class NewsResponse (
    val timePosted: Int,
    val articles: List<NewsArticle>
    )

@Entity(tableName = "news_articles")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var timePosted: Long,
    var title: String,
    var description: String,
    var urlToImage: String)


