package com.luodlin.smarthomedemo

import android.content.Context
import com.luodlin.smarthomedemo.data.datasource.local.FakeLocalDataSource
import com.luodlin.smarthomedemo.data.datasource.local.LocalDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.FakeRemoteDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.RemoteDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.RetrofitDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.retrofit.RetrofitClient
import com.luodlin.smarthomedemo.data.repository.sign.SignInRepository
import com.luodlin.smarthomedemo.data.repository.sign.SignInRepositoryImpl

class AppContainerImpl : AppContainer {

    private val localDataSource: LocalDataSource by lazy { FakeLocalDataSource() }
    private val remoteDataSource: RemoteDataSource by lazy { RetrofitDataSource(RetrofitClient.apiService) }

    override val signInRepository: SignInRepository by lazy {
        SignInRepositoryImpl(localDataSource, remoteDataSource)
    }

}