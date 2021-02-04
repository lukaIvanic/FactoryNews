package com.example.factoryzadatak.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.factoryzadatak.data.model.NewsArticle
import com.example.factoryzadatak.repository.NewsRepository

class NewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var news: MutableLiveData<List<NewsArticle>> = repository.getNews()


    fun cancelJobs(){
        repository.cancelJobs()
    }


}