package com.rogergcc.qrunlockclientapp.di

import com.rogergcc.qrunlockclientapp.data.network.QrEventInterceptor
import com.rogergcc.qrunlockclientapp.data.network.QrEventsApi
import com.rogergcc.qrunlockclientapp.common.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{

        return OkHttpClient.Builder()
            .addInterceptor(QrEventInterceptor())
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constantes.TMDBAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): QrEventsApi {
        return retrofit.create(QrEventsApi::class.java)
    }

}