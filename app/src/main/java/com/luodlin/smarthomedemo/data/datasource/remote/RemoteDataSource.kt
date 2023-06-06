package com.luodlin.smarthomedemo.data.datasource.remote

import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkBasicEntity
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkFetchCode
import kotlin.jvm.Throws

interface RemoteDataSource {

    /**
     * 获取登录验证码
     */
    suspend fun fetchCode(phone: String, type: Int): NetworkFetchCode

    /**
     * 验证码登录
     */
    suspend fun phoneSignIn(phone: String, code: String): NetworkSignIn

    /**
     * 密码登录
     */
    suspend fun passwordSignIn(phone: String, password: String): NetworkSignIn

    /**
     * 退出
     */
    suspend fun signOut(phone: String)

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(token: String)

}

