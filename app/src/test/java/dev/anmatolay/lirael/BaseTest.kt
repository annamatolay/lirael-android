package dev.anmatolay.lirael

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.analytics.impl.FakeAnalyticsImpl
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.impl.FakeAuthenticatorImpl
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.core.threading.impl.TestSchedulerProvider
import dev.anmatolay.lirael.util.MockUserPropertyImpl
import dev.anmatolay.lirael.util.UserProperty
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.spyk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule

open class BaseTest : KoinTest {

    protected val fakeAnalytics = spyk<FakeAnalyticsImpl>()
    protected val fakeAuthenticator = spyk<FakeAuthenticatorImpl>()
    protected val sharedPrefHandler: SharedPrefHandler =  mockk(relaxed = true)


    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz, relaxed = true)
    }

    @Before
    open fun setUp() {
        startTestKoin()
    }

    @After
    open fun tearDown() {
        stopKoin()
    }

    fun startTestKoin(
        vararg additionalModules: Module = emptyArray(),
        moduleProvider: Module.() -> Unit = {},
    ) {
        startKoin {
            androidContext(mockk())
            modules(
                module {
                    factory<SchedulerProvider> { TestSchedulerProvider() }
                    factory<Authenticator> { fakeAuthenticator }
                    factory<AnalyticsWrapper> { fakeAnalytics }
                    factory { sharedPrefHandler }
                    factory<UserProperty> { MockUserPropertyImpl }
                    mockFirebase()
                }
            )
            modules(additionalModules.toList())
            modules(
                module {
                    moduleProvider.invoke(this)
                },
            )
        }
    }

    private fun Module.mockFirebase() {
        factory<FirebaseAnalytics> { mockk(relaxed = true) }
        factory<FirebaseCrashlytics> { mockk(relaxed = true) }
        factory<FirebaseAuth> { mockk(relaxed = true) }
    }
}
