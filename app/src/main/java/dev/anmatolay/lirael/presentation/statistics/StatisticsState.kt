package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.domain.model.User

data class StatisticsState(
    val userRecipeStat: User.RecipeStatistic,
): UiState
