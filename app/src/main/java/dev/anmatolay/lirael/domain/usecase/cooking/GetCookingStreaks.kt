package dev.anmatolay.lirael.domain.usecase.cooking

import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.domain.model.CookingHistory
import dev.anmatolay.lirael.domain.model.CookingStrike
import dev.anmatolay.lirael.util.Constants.KEY_COOKING_STREAK_LONGEST
import dev.anmatolay.lirael.util.LocalDateProvider
import java.time.LocalDate

class GetCookingStreaks(
    private val sharedPrefHandler: SharedPrefHandler,
    private val localDateProvider: LocalDateProvider,
    private val getCookingHistoryUseCase: GetCookingHistoryUseCase,
) {

    operator fun invoke(): CookingStrike {

        val currentStreak = calculateCurrentCookingStrike()

        return CookingStrike(current = currentStreak, longest = getLongestStreak(currentStreak))
    }

    private fun calculateCurrentCookingStrike(): Int {
        val cookingHistoryList = mutableListOf<CookingHistory>()
        val currentDate = localDateProvider.now()

        getCookingHistoryUseCase(localDateProvider.getFormattedMonthAndYear(currentDate))?.let {
            cookingHistoryList.addAll(it)
        }

        if (cookingHistoryList.isEmpty()) return 0

        return calculateCurrentStreak(currentDate, cookingHistoryList)
    }

    private fun calculateCurrentStreak(
        currentDate: LocalDate,
        cookingHistoryList: MutableList<CookingHistory>,
    ): Int {
        var currentStreak = 0
        var selectedDate = currentDate

        with(cookingHistoryList) {
            addAllIfContainsFirstDayOfMonth(currentDate)
            sortBy { it.epochDateTime }
            reverse()
            forEachIndexed { index, cookingHistory ->
                if (
                    cookingHistory.epochDateTime == selectedDate.toEpochDay() ||
                    cookingHistory.isCookedYesterday(currentDate, index)
                ) {
                    ++currentStreak
                    selectedDate =
                        if (cookingHistory.isCookedYesterday(currentDate, index))
                            selectedDate.minusDays(2)
                        else
                            selectedDate.minusDays(1)
                } else {
                    return@with
                }
            }
        }

        return currentStreak
    }

    private fun MutableList<CookingHistory>.addAllIfContainsFirstDayOfMonth(localDate: LocalDate) {
        val firstDayOfCurrentMonth = localDate.withDayOfMonth(1).toEpochDay()

        if (this.contains(firstDayOfCurrentMonth)) {
            val previousMonth = localDate.minusMonths(1)
            getCookingHistoryUseCase(localDateProvider.getFormattedMonthAndYear(previousMonth))?.let {
                this.addAll(it)
            }
            if (this.contains(previousMonth.toEpochDay())) {
                this.addAllIfContainsFirstDayOfMonth(previousMonth)
            }
        }
    }

    private fun List<CookingHistory>.contains(timeInEpoch: Long): Boolean {
        var isEpochTimeInList = false

        this.forEach { cookingHistory ->
            if (cookingHistory.epochDateTime == timeInEpoch) isEpochTimeInList = true
        }

        return isEpochTimeInList
    }

    private fun CookingHistory.isCookedYesterday(
        currentDate: LocalDate,
        index: Int,
    ) = this.epochDateTime == currentDate.minusDays(1).toEpochDay() && index == 0

    private fun getLongestStreak(currentStreak: Int): Int {
        val longestStreak = sharedPrefHandler.getInt(KEY_COOKING_STREAK_LONGEST)
        return if (longestStreak < currentStreak) {
            sharedPrefHandler.putInt(KEY_COOKING_STREAK_LONGEST, currentStreak)
            currentStreak
        } else {
            longestStreak
        }
    }
}
