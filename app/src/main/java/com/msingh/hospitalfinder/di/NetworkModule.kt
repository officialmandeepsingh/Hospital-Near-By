package com.msingh.hospitalfinder.di

import com.msingh.hospitalfinder.data.remote.GooglePlaceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providePlacesApiService(retrofit: Retrofit): GooglePlaceApiService {
        return retrofit.create(GooglePlaceApiService::class.java)
    }

}