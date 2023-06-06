package com.luodlin.smarthomedemo.data.datasource.remote

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkBasicEntity
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkFetchCode
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignInData
import kotlinx.coroutines.delay

class FakeRemoteDataSource : RemoteDataSource {
    var token = "jaiofjaiofjaiojf"
    override suspend fun fetchCode(phone: String, type: Int): NetworkFetchCode {
        delay(1500)
        return NetworkFetchCode(code = 200)
    }

    override suspend fun phoneSignIn(phone: String, code: String): NetworkSignIn {
        delay(1500)
        token += "1"
        return NetworkSignIn(
            code = 200,
            data = NetworkSignInData(signInType = 2, token = token),
            message = "登录成功"
        )
    }

    override suspend fun passwordSignIn(phone: String, password: String): NetworkSignIn {
        delay(1500)
        token += "1"
        return NetworkSignIn(
            code = 200,
            data = NetworkSignInData(signInType = 1, token = token),
            message = "登录成功"
        )
    }

    override suspend fun signOut(phone: String) {
    }

    override suspend fun getUserInfo(token: String) {

    }
}