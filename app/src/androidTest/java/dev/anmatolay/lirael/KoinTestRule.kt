package dev.anmatolay.lirael

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.analytics.impl.FakeAnalyticsImpl
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.impl.FakeAuthenticatorImpl
import dev.anmatolay.lirael.core.di.module.*
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.core.threading.impl.TestSchedulerProvider
import dev.anmatolay.lirael.util.MockUserPropertyImpl
import dev.anmatolay.lirael.util.UserProperty
import io.mockk.mockk
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

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
                viewModelModule,
                repositoryModule,
                useCaseModule,
                dataSourceModule,
                databaseModule,
            )
            module {
                factory<SchedulerProvider> { TestSchedulerProvider() }
                factory<Authenticator> { FakeAuthenticatorImpl() }
                factory<AnalyticsWrapper> { FakeAnalyticsImpl() }
                factory<UserProperty> { MockUserPropertyImpl }
                factory<FirebaseAnalytics> { mockk(relaxed = true) }
                factory<FirebaseCrashlytics> { mockk(relaxed = true) }
                factory<FirebaseAuth> { mockk(relaxed = true) }
            }
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}
