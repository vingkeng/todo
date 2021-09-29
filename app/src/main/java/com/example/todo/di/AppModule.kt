package com.example.todo.di

import android.app.Application
import com.example.todo.db.AppDatabase
import com.example.todo.db.AppDbDao
import com.example.todo.network.RetroServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        const val BASE_URL = "https://607fd2fea5be5d00176dc52b.mockapi.io/"
    }

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getDbInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDbDao(appDatabase: AppDatabase): AppDbDao {
        return appDatabase.getAppDbDao()
    }

    @Provides
    @Singleton
    fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInterface {
        return retrofit.create(RetroServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}