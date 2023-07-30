package com.arjun.radiusagentweather.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

//    @Binds
//    abstract fun bindPreferences(preferences: DefaultPreferences): Preferences

    companion object {

        @Provides
        fun providerSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        }

    }
}