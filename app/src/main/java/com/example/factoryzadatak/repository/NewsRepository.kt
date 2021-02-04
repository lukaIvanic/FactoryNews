package com.example.factoryzadatak.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.factoryzadatak.api.NewsApiService
import com.example.factoryzadatak.data.NewsDao
import com.example.factoryzadatak.data.model.NewsArticle
import com.example.factoryzadatak.data.model.NewsResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApiService: NewsApiService
) {
    companion object {
        var job: CompletableJob? = null
    }

    fun getNews(): MutableLiveData<List<NewsArticle>> {

        job = Job()

        return object : MutableLiveData<List<NewsArticle>>() {

            override fun onActive() {
                super.onActive()

                job?.let { apiJob ->
                    CoroutineScope(IO + apiJob).launch {

                        // News from RoomDB
                        val fiveMinsAgo: Long = System.currentTimeMillis() - (5 * 60 * 1000)
                        val newsArticles: List<NewsArticle> = newsDao.getRecentNews(fiveMinsAgo)
                        if (!newsArticles.isNullOrEmpty()) {
                            withContext(Main) {
                                value = newsArticles
                                apiJob.complete()
                                cancel()
                            }
                        }

                        // News from API
                        try {
                            val newsResponse: NewsResponse = newsApiService.getNews()
                            val iterator = newsResponse.articles.iterator()
                            val currentTime = System.currentTimeMillis()
                            while (iterator.hasNext()) {
                                iterator.next().timePosted = currentTime
                            }
                            // Caching
                            newsDao.insertNews(newsResponse.articles)
                            withContext(Main) {
                                value = newsResponse.articles
                                apiJob.complete()
                            }
                        } catch (e: Exception) {
                            postValue(null)
                            apiJob.complete()
                        }
                    }
                }
            }
        }


    }

    fun cancelJobs() {
        job?.cancel()
    }


}