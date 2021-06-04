package id.capstone.project.skindetector.app

import android.app.Application
import id.capstone.project.skindetector.di.core.networkModule
import id.capstone.project.skindetector.di.core.repositoryModule
import id.capstone.project.skindetector.di.useCaseModule
import id.capstone.project.skindetector.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
//                    ,databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}