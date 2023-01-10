package com.test.testmovies.app

import android.app.Application
import com.test.testmovies.di.AppComponent
import com.test.testmovies.di.AppModule
import com.test.testmovies.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}