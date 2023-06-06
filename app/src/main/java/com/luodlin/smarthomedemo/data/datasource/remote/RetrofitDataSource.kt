package com.luodlin.smarthomedemo.data.datasource.remote

import android.util.Log
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkFetchCode
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.SignInInfo
import com.luodlin.smarthomedemo.data.datasource.remote.retrofit.ApiService
import kotlin.jvm.Throws

class RetrofitDataSource(private val apiService: ApiService) : RemoteDataSource {

    @Throws(Exception::class)
    override suspend fun fetchCode(phone: String, type: Int): NetworkFetchCode =
        apiService.fetchCode(phone, type)


    @Throws(Exception::class)
    override suspend fun phoneSignIn(phone: String, code: String): NetworkSignIn =
        apiService.signIn(SignInInfo(phone = phone, validCode = code, type = 1))


    @Throws(Exception::class)
    override suspend fun passwordSignIn(phone: String, password: String): NetworkSignIn =
        apiService.signIn(SignInInfo(phone = phone, password = password, type = 2))


    override suspend fun signOut(phone: String) {
    }

    override suspend fun getUserInfo(token: String) {
        TODO("Not yet implemented")
    }

}




