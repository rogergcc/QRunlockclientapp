package com.rogergcc.qrunlockclientapp.di

import com.google.gson.GsonBuilder
import com.rogergcc.qrunlockclientapp.BuildConfig
import com.rogergcc.qrunlockclientapp.common.Constants
import com.rogergcc.qrunlockclientapp.data.EventAttendanceRepositoryImpl
import com.rogergcc.qrunlockclientapp.data.network.QrEventClient
import com.rogergcc.qrunlockclientapp.data.network.QrEventInterceptor
import com.rogergcc.qrunlockclientapp.data.network.QrEventsApi
import com.rogergcc.qrunlockclientapp.domain.repository.IEventAttendanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(QrEventInterceptor())
            .addInterceptor(loggingInterceptor)

//            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)

            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.ATTENDANCE_LOCAL_API_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): QrEventsApi = retrofit.create(QrEventsApi::class.java)

    @Singleton
    @Provides
    fun provideEventAttendanceRepository(
        apiClient: QrEventClient
    ): IEventAttendanceRepository {
        return EventAttendanceRepositoryImpl(
            apiClient
        )
    }
}