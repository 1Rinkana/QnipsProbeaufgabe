package com.myapp.qnipsprobeaufgabe

import android.app.Application
import com.myapp.qnipsprobeaufgabe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MenuApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MenuApplication)
            modules(appModule)
        }
    }
}
