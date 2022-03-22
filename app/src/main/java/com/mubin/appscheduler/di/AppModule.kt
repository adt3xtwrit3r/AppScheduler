package com.mubin.appscheduler.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mubin.appscheduler.api.RetrofitUtils.retrofitInstance
import com.mubin.appscheduler.api.endpoint.ApiService
import com.mubin.appscheduler.api.endpoint.ApiService2
import com.mubin.appscheduler.db.AppDao
import com.mubin.appscheduler.db.AppDataBase
import com.mubin.appscheduler.helper.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("apiMovies")
    fun provideBaseUrlMovies1() = Constants.BASE_URL

    @Provides
    @Named("apiMovies2")
    fun provideBaseUrlMovies2() = Constants.BASE_URL2

    @Provides
    @Singleton
    fun provideRetrofitInstance1(@Named("apiMovies") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance2(@Named("apiMovies2") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService2 =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(ApiService2::class.java)

    @Provides
    @Singleton
    fun buildDatabase(@ApplicationContext context: Context) : AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "app_db").build()
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDataBase): AppDao {
        return appDatabase.getAppDao()
    }

}