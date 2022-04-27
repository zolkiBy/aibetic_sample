package com.example.aibetictest

import android.app.Application
import com.example.aibetictest.common.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }

            androidContext(this@App)
            modules(MainModule().module)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}