package dev.anmatolay.lirael.core.di.module

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.analytics.impl.FirebaseAnalyticsImpl
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.impl.FirebaseAuthenticatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    factory<Authenticator> { FirebaseAuthenticatorImpl(get()) }
    factory<AnalyticsWrapper> { FirebaseAnalyticsImpl(get()) }
    single { FirebaseCrashlytics.getInstance() }
    single { FirebaseAnalytics.getInstance(androidContext()) }
    single { FirebaseAuth.getInstance() }
}
