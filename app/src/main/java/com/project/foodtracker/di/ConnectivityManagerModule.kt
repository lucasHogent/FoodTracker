package com.project.foodtracker.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ConnectivityManagerModule {
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}