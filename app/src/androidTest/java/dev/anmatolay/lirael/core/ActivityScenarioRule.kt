package dev.anmatolay.lirael.core

import android.app.Activity
import android.content.Intent

object ActivityScenarioRule {
    inline fun <reified A : Activity> lazyActivityScenarioRule(
        launchActivity: Boolean = true,
        noinline intentSupplier: () -> Intent
    ): LazyActivityScenarioRule<A> =
        LazyActivityScenarioRule(launchActivity, intentSupplier)

    inline fun <reified A : Activity> lazyActivityScenarioRule(
        launchActivity: Boolean = true,
        intent: Intent? = null
    ): LazyActivityScenarioRule<A> = if (intent == null) {
        LazyActivityScenarioRule(launchActivity, A::class.java)
    } else {
        LazyActivityScenarioRule(launchActivity, intent)
    }
}
