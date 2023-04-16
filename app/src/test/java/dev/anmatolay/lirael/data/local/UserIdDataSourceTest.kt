package dev.anmatolay.lirael.data.local

import dev.anmatolay.lirael.util.Constants.KEY_USER_ID
import dev.anmatolay.lirael.util.TestConstants.TEST_USER_ID
import dev.anmatolay.lirael.BaseTest
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import org.junit.Test

class UserIdDataSourceTest : BaseTest() {

    private val dataSource = UserIdDataSource(sharedPrefHandler)

    @Test
    fun `Given user not cached When getUserId called Then return with empty Maybe`() {
        // Given
        every { sharedPrefHandler.getString(KEY_USER_ID) } returns null

        // When
        val result = dataSource.getUserId().test()

        // Then
        result.run {
            assertNoValues()
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `Given user cached When getUserId called Then return with ID`() {
        // Given
        every { sharedPrefHandler.getString(KEY_USER_ID) } returns TEST_USER_ID

        // When
        val result = dataSource.getUserId().test()

        // Then
        result.run {
            assertValue(TEST_USER_ID)
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `Given string ID When putUserId called Then saved with shared pref`() {
        // Given
        justRun { sharedPrefHandler.putString(KEY_USER_ID, TEST_USER_ID) }

        // When
        dataSource.putUserId(TEST_USER_ID)

        // Then
        verify { sharedPrefHandler.putString(KEY_USER_ID, TEST_USER_ID) }
    }
}
