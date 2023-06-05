package com.luodlin.smarthomedemo.data.datasource.remote

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import kotlinx.coroutines.delay

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun fetchCode(phone: String): Result<Nothing> {
        delay(1500)
        return Result.Success(null)
    }

    override suspend fun phoneSignIn(phone: String, code: String): Result<NetworkSignIn> {
        delay(1500)
        return Result.Success(NetworkSignIn(2, "ajsdiasjioda"))
    }

    override suspend fun passwordSignIn(phone: String, password: String): Result<NetworkSignIn> {
        delay(1500)
        return Result.Success(NetworkSignIn(1, "ajsdiasjioda"))
    }


    override suspend fun signOut(phone: String) {
    }

    override suspend fun getUserInfo(token: String) {

    }
}