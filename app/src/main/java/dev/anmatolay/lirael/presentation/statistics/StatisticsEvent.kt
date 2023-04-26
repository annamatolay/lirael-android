package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class StatisticsEvent : UiEvent {

    object GetUserStatistics: StatisticsEvent()

    object RetryGetStatOnClicked : StatisticsEvent()

    object GetRandomRecipes : StatisticsEvent()

    object RetryGetRandomRecipes : StatisticsEvent()
}
