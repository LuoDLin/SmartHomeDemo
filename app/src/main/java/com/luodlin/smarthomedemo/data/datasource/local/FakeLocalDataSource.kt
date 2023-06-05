package com.luodlin.smarthomedemo.data.datasource.local

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.model.Sign

class FakeLocalDataSource : LocalDataSource {
    override suspend fun saveSignInInfo(data: Result<Sign>) {
    }


    override fun readSignInInfo(): Result<Sign> {
        return Result.Success(Sign(1, "ajiogaidgoa"))
    }
}

