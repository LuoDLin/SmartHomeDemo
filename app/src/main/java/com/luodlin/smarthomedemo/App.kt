package com.luodlin.smarthomedemo

import android.app.Application

class App : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }


}