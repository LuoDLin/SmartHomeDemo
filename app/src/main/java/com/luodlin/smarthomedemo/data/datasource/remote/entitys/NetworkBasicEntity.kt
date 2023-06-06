package com.luodlin.smarthomedemo.data.datasource.remote.entitys

open class NetworkBasicEntity<T>(
    val code: Int , val message: String?, val data: T?
)