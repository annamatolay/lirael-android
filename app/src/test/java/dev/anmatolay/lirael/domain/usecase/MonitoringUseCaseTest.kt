package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.util.TestConstants.TEST_USER_ID
import dev.anmatolay.lirael.BaseTest
import dev.anmatolay.lirael.util.MockUserPropertyImpl.TEST_VALUE_INT
import dev.anmatolay.lirael.util.MockUserPropertyImpl.TEST_VALUE_STRING
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_ANDROID_VERSION
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_API_LEVEL
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_APP_VERSION
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.koin.core.component.get

class MonitoringUseCaseTest : BaseTest() {

    private lateinit var monitoringUseCase: MonitoringUseCase

    @Before
    override fun setUp() {
        super.setUp()
        monitoringUseCase = MonitoringUseCase(get(), get(), get())
    }

    @Test
    fun `When setUserProperties Then AnalyticsWrapper set correct user properties`() {
        // When
        monitoringUseCase.setUserProperties()

        verify { fakeAnalytics.setUserProperty(KEY_APP_VERSION, TEST_VALUE_STRING) }
        verify { fakeAnalytics.setUserProperty(KEY_ANDROID_VERSION, TEST_VALUE_STRING) }
        verify { fakeAnalytics.setUserProperty(KEY_API_LEVEL, TEST_VALUE_INT.toString()) }
    }

    @Test
    fun`When setUpAnalyticsAndLogging Then DiamondDebugTree used`(){
        // When
        monitoringUseCase.setUpAnalyticsAndLogging(TEST_USER_ID)

        // Then
        verify { fakeAnalytics.setUserId(TEST_USER_ID) }
    }
}
