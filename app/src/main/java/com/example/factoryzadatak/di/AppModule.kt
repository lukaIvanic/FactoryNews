package com.example.factoryzadatak.di

import android.app.Application
import androidx.room.Room
import com.example.factoryzadatak.api.NewsApiService
import com.example.factoryzadatak.data.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, NewsDatabase::class.java, "news_articles_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideNewsDao(db: NewsDatabase) = db.newsDao()

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(NewsApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideNewsAPI(retrofit: Retrofit):NewsApiService = retrofit.create(NewsApiService::class.java)
}