package com.luodlin.smarthomedemo.data.datasource.local

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.model.Sign

class FakeLocalDataSource : LocalDataSource {
    override suspend fun saveSignInInfo(data: Sign) {
    }

    override fun readSignInInfo(): Sign = Sign(isSignIn = false, message = "首次登录")

}

