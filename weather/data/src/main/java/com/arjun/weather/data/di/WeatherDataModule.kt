package com.arjun.weather.data.di

import android.content.Context
import com.arjun.weather.data.local.db.WeatherDb
import com.arjun.weather.data.remote.api.WeatherApi
import com.arjun.weather.data.repository.WeatherRepositoryImp
import com.arjun.weather.domain.repository.WeatherRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherDataModule {

    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImp: WeatherRepositoryImp): WeatherRepository

    companion object {
        @Provides
        fun providesHttpInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        fun providesOkhttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient =
            OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        @Provides
        fun providesConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

        @Provides
        fun providesMoshi() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        @Provides
        fun providesRetrofit(
            okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory,
        ): Retrofit = Retrofit.Builder().baseUrl(WeatherApi.BASE_URL).client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        @Provides
        fun providesWeatherApi(retrofit: Retrofit): WeatherApi =
            retrofit.create(WeatherApi::class.java)

        @Provides
        fun providesWeatherDb(@ApplicationContext context: Context): WeatherDb =
            WeatherDb.getInstance(context)

        @Provides
        fun providesWeatherDao(weatherDb: WeatherDb) = weatherDb.weatherDao()
    }
}