package com.luodlin.smarthomedemo.data.datasource.remote.retrofit

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkFetchCode
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.SignInInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    //获取验证码
    @GET("valid")
    suspend fun fetchCode(
        @Query("phone") phone: String, @Query("type") type: Int
    ): NetworkFetchCode

    //登陆
    @POST("user/login")
    suspend fun signIn(@Body info: SignInInfo): NetworkSignIn

    /**
     * 退出
     */
    suspend fun logout(phone: String): NetworkSignIn
}