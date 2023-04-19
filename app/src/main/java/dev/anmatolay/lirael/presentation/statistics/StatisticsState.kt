package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.domain.model.User

data class StatisticsState(
    val name: String? = null,
    val userRecipeStat: User.RecipeStatistic? = null,
    val error: Error? = null,
): UiState {
    enum class Error {
        STAT_READ_ERROR
    }
}
