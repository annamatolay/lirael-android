package dev.anmatolay.lirael.domain.usecase.cooking

import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.domain.model.CookingHistory
import dev.anmatolay.lirael.util.CookingHistoryMap
import dev.anmatolay.lirael.util.LocalDateProvider

class GetCookingHistoryUseCase(
    private val sharedPrefHandler: SharedPrefHandler,
    private val localDateProvider: LocalDateProvider,
) {

    operator fun invoke() =
        sharedPrefHandler
            .getCookingHistoryMap()?.get(localDateProvider.getFormattedMonthAndYear(localDateProvider.now()))
            ?.toCookingHistory()

    private fun dateInEpoch() = localDateProvider.now().toEpochDay()

    operator fun invoke(formattedMonthAndYear: String = "") =
        sharedPrefHandler
            .getCookingHistoryMap()?.get(formattedMonthAndYear)
            ?.map { CookingHistory(it.key, it.value) }

    private fun Map<Long, Int?>?.toCookingHistory(): CookingHistory? =
        if (this == null)
            null
        else
            CookingHistory(dateInEpoch(), this[dateInEpoch()] ?: 0)
}