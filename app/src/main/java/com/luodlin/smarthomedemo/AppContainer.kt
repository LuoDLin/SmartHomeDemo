package com.luodlin.smarthomedemo


import com.luodlin.smarthomedemo.data.repository.sign.SignInRepository

interface AppContainer {
    val signInRepository: SignInRepository
}
