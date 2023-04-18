package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.data.repository.UserCacheRepository
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.Constants.USER_DEFAULT_ID
import dev.anmatolay.lirael.util.TestConstants
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetUserUseCaseTest {

    private val repository = mockk<UserCacheRepository>()
    private val getUserUseCase = GetUserUseCase(repository)

    @Test
    fun `Given user cached When getUserUseCase called Then return with User`() {
        // Given
        every {
            repository.getCachedOrDefaultUser()
        } returns Single.just(User(TestConstants.TEST_USER_ID))

        // Then
        val result = getUserUseCase().test()

        // When
        result.run {
            assertNoErrors()
            assertValue(User(TestConstants.TEST_USER_ID))
            assertComplete()
        }
    }

    @Test
    fun `Given user id NOT cached When getCachedOrDefaultUser Then return with default User`() {
        // Given
        every { repository.getCachedOrDefaultUser() } returns
                Single.just(User(USER_DEFAULT_ID))

        // Then
        val result = getUserUseCase().test()

        // When
        result.run {
            assertNoErrors()
            assertValue(User(USER_DEFAULT_ID))
            assertComplete()
        }
    }
}
