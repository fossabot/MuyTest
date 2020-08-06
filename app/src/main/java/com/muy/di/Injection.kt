package com.muy.di

import androidx.lifecycle.ViewModelProvider
import com.muy.App
import com.muy.data.DataSource
import com.muy.data.MuyRepository
import com.muy.data.database.MuyDatabase
import com.muy.data.local.LocalRepository
import com.muy.data.local.LocalSource
import com.muy.data.remote.RemoteRepository
import com.muy.data.remote.RemoteSource
import com.muy.data.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class Injection(app: App) {


    private val apiClient: ApiClient.ServicesApiInterface =
        ApiClient.build()!!

    private val database =  MuyDatabase.getInstance(app)


    private val coroutineContext: CoroutineContext
        get() = provideCoroutineContext()

    private val localRepository: LocalSource =
        LocalRepository(coroutineContext,database)

    private val remoteRepository: RemoteSource =
        RemoteRepository(apiClient)

    private val museumDataSource: DataSource =
        MuyRepository(remoteRepository,localRepository)

    private val museumViewModelFactory =
        ViewModelFactory(providerRepository())

    fun providerRepository(): DataSource {
        return museumDataSource
    }

    fun providerDatabase(): MuyDatabase {
        return database
    }

    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.Main
    }


    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return museumViewModelFactory
    }

}