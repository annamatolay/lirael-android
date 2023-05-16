package dev.anmatolay.lirael.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dev.anmatolay.lirael.KoinTestRule
import dev.anmatolay.lirael.core.ActivityScenarioRule.lazyActivityScenarioRule
import dev.anmatolay.lirael.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.module.Module
import org.koin.test.KoinTest

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
open class InstrumentedTest(
    val launchActivity: Boolean = true,
    val modules: MutableList<Module> = mutableListOf(),
    val mockEndpointList: MutableList<MockServer.MockEndpoint> = mutableListOf()
) : KoinTest {

    @get:Rule
    val activityRule = lazyActivityScenarioRule<MainActivity>(launchActivity)

    @get:Rule
    val koinTestRule = KoinTestRule(modules)

    val context: Context = ApplicationProvider.getApplicationContext()

    private lateinit var server: MockServer

    @Before
    fun setUp() {
        server = MockServer(mockEndpointList)
        server.start()
    }

    @After
    fun tearDown() {
        server.stop()
    }
}
