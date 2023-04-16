package dev.anmatolay.lirael

import androidx.test.platform.app.InstrumentationRegistry
import dev.anmatolay.lirael.core.di.module.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestRule(
    private val modules: List<Module>
) : TestWatcher() {
    override fun starting(description: Description) {
        // Stop Koin started out of control
        stopKoin()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(
                appModule,
                firebaseModule,
                viewModelModule,
                repositoryModule,
                useCaseModule,
                dataSourceModule,
            )
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}
