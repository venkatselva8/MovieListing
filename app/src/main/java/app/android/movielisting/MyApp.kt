package app.android.movielisting

import android.app.Application
import app.android.movielisting.di.appModule
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}
