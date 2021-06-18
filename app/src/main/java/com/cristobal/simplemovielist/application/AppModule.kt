package com.cristobal.simplemovielist.application

import android.content.Context

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

 /*   @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

    @Provides
    @Singleton
    fun provideParseRepository(): ParseRepository = ParseRepository()

    @Provides
    @Singleton
    fun provideAmazonManager(): AmazonS3Manager = AmazonS3Manager()

    @Provides
    @Singleton
    fun provideContactsRepositoru(@ApplicationContext appContext: Context): ContactsRepository = ContactsRepository(appContext)*/
}