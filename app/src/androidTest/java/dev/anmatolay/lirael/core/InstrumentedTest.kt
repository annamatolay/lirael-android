package dev.anmatolay.lirael.core

import androidx.test.ext.junit.rules.ActivityScenarioRule
import dev.anmatolay.lirael.KoinTestRule
import dev.anmatolay.lirael.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.module.Module
import org.koin.test.KoinTest

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
open class InstrumentedTest(
    activityScenarioRule: ActivityScenarioRule<*> = ActivityScenarioRule(MainActivity::class.java),
    val modules: MutableList<Module> = mutableListOf(),
    val mockEndpointList: MutableList<MockServer.MockEndpoint> = mutableListOf()
) : KoinTest {

    @get:Rule
    val activityRule = activityScenarioRule

    @get:Rule
    val koinTestRule = KoinTestRule(modules)

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
