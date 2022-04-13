package com.fxingsign.profitrackr.di

import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.data.repository.StockTradeRepositoryImpl
import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(StockQuoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideStockQuoteApi(retrofit: Retrofit): StockQuoteApi {
        return retrofit.create(StockQuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockTradeRepository(): StockTradeRepository {
        return StockTradeRepositoryImpl()
    }

}