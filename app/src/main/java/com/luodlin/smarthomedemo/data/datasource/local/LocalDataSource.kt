package com.luodlin.smarthomedemo.data.datasource.local

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.model.Sign

interface LocalDataSource {
    suspend fun saveSignInInfo(data: Sign)
    fun readSignInInfo(): Sign
}
