package dev.anmatolay.lirael.domain.usecase.cooking

import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.domain.model.CookingHistory
import dev.anmatolay.lirael.util.CookingHistoryMap
import dev.anmatolay.lirael.util.LocalDateProvider

class PutCookingHistoryUseCase(
    private val sharedPrefHandler: SharedPrefHandler,
    private val localDateProvider: LocalDateProvider,
) {

    operator fun invoke(cookingHistory: CookingHistory) =
        with(sharedPrefHandler.getCookingHistoryMap()) {
            if (this == null) {
                sharedPrefHandler.putCookingHistoryMap(cookingHistory.toCookingHistoryMap())
            } else {
                val formattedMonthAndYear = localDateProvider.getFormattedMonthAndYear(localDateProvider.now())
                val cookingHistoryEntry = this[formattedMonthAndYear]
                val cookingHistoryMap = this.toMutableMap()

                if (cookingHistoryEntry == null) {
                    cookingHistoryMap[formattedMonthAndYear] = cookingHistory.toMap()
                } else {
                    val cookingHistoryEntryMap = cookingHistoryEntry.toMutableMap()

                    cookingHistoryEntryMap[cookingHistory.epochDateTime] = cookingHistory.numberOfCooking
                    cookingHistoryMap[formattedMonthAndYear] = cookingHistoryEntryMap
                }
                sharedPrefHandler.putCookingHistoryMap(cookingHistoryMap)
            }
        }

    private fun CookingHistory.toCookingHistoryMap(): CookingHistoryMap =
        mapOf(
            localDateProvider.getFormattedMonthAndYear(localDateProvider.now())
                to
                mapOf(this.epochDateTime to this.numberOfCooking)
        )

    private fun CookingHistory.toMap(): Map<Long, Int> = mapOf(this.epochDateTime to this.numberOfCooking)
}

