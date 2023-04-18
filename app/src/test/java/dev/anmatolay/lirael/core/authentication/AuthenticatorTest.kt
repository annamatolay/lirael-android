package dev.anmatolay.lirael.core.authentication

import dev.anmatolay.lirael.BaseTest
import dev.anmatolay.lirael.domain.usecase.CacheUserIdUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import dev.anmatolay.lirael.util.Constants.USER_DEFAULT_ID
import dev.anmatolay.lirael.util.TestConstants.TEST_USER_ID
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test

class AuthenticatorTest : BaseTest() {

    private val cacheUserIdUseCase = mockk<CacheUserIdUseCase>()
    private val monitoringUseCase = mockk<MonitoringUseCase>()

    @Before
    override fun setUp() {
        startTestKoin {
            factory { cacheUserIdUseCase }
            factory { monitoringUseCase }
        }
    }

    @Test
    fun `When userProvider changed Then cacheUserId and setUpAnalyticsAndLogging`() {
        every { cacheUserIdUseCase(TEST_USER_ID) } returns Completable.complete()
        justRun { monitoringUseCase.setUpAnalyticsAndLogging(TEST_USER_ID) }
        fakeAuthenticator.userId = TEST_USER_ID
        fakeAuthenticator.isSuccessful = true

        // Then
        val result = fakeAuthenticator.signInAnonymously().test()

        // When
        result.run {
            assertNoErrors()
            assertComplete()
        }
        verifyAll {
            cacheUserIdUseCase(TEST_USER_ID)
            monitoringUseCase.setUpAnalyticsAndLogging(TEST_USER_ID)
        }
    }

    @Test
    fun `Given authentication unsuccessful When userProvider NOT changed Then cacheUserId and setUpAnalyticsAndLogging NOT called`() {
        // Given
        fakeAuthenticator.userId = USER_DEFAULT_ID
        fakeAuthenticator.isSuccessful = false

        // Then
        val result = fakeAuthenticator.signInAnonymously().test()

        // When
        result.run {
            assertNotComplete()
            assertFailure(UnknownAuthErrorException::class.java)
        }
        verify(exactly = 0) { cacheUserIdUseCase(TEST_USER_ID) }
        verify(exactly = 0) { monitoringUseCase.setUpAnalyticsAndLogging(TEST_USER_ID) }
    }
}
