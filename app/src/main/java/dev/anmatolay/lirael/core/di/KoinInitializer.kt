package dev.anmatolay.lirael.core.di

import android.app.Application
import dev.anmatolay.lirael.core.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinInitializer {

    fun init(application: Application) {
        startKoin {
            allowOverride(true)
            androidContext(application)
            modules(
                appModule,
                firebaseModule,
                viewModelModule,
                useCaseModule,
                repositoryModule,
                dataSourceModule,
                databaseModule,
            )
        }
    }
}
