package com.luodlin.smarthomedemo.data.datasource.remote

import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.Result

interface RemoteDataSource {

    /**
     * 获取登录验证码
     */
    suspend fun fetchCode(phone: String): Result<Nothing>

    /**
     * 验证码登录
     */
    suspend fun phoneSignIn(phone: String, code: String): Result<NetworkSignIn>

    /**
     * 密码登录
     */
    suspend fun passwordSignIn(phone: String, password: String): Result<NetworkSignIn>

    /**
     * 退出
     */
    suspend fun signOut(phone: String)

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(token: String)

}

