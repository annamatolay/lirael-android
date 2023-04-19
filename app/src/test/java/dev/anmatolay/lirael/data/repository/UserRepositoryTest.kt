package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.BaseTest
import dev.anmatolay.lirael.data.local.UserIdDataSource
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.Constants.USER_DEFAULT_ID
import dev.anmatolay.lirael.util.TestConstants.TEST_USER_ID
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Maybe
import org.junit.Test

class UserRepositoryTest : BaseTest() {

    private val dataSource = mockk<UserIdDataSource>()
    private val repository = UserRepository(dataSource)

    @Test
    fun `Given user id cached When getCachedOrDefaultUser called Then return with User`() {
        // Given
        every { dataSource.getUserId() } returns Maybe.just(TEST_USER_ID)

        // Then
        val result = repository.getUserOrDefault().test()

        // When
        result.run {
            assertNoErrors()
            assertValue(User(TEST_USER_ID))
            assertComplete()
        }
    }

    @Test
    fun `Given user id NOT cached When getCachedOrDefaultUser called Then return with default User`() {
        // Given
        every { dataSource.getUserId() } returns Maybe.empty()

        // Then
        val result = repository.getUserOrDefault().test()

        // When
        result.run {
            assertNoErrors()
            assertValue(User(USER_DEFAULT_ID))
            assertComplete()
        }
    }

    @Test
    fun `When cacheUserId called Then update User`() {
        justRun { dataSource.putUserId(TEST_USER_ID) }

        // Then
        val result = repository.cache(TEST_USER_ID).test()

        // When
        result.run {
            assertNoErrors()
            assertComplete()
        }
        verify { dataSource.putUserId(TEST_USER_ID) }
    }
}
