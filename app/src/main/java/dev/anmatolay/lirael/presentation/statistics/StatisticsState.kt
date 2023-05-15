package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.domain.model.CookingHistory
import dev.anmatolay.lirael.domain.model.CookingStrike
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User

//TODO: clean-up?
sealed class StatisticsState(
    open val name: String? = null,
    open val userRecipeStat: User.RecipeStatistic? = null,
    open val recipes: List<Recipe>? = null,
    open val isRecipesLoading: Boolean = false,
    open val error: Error? = null,
) : UiState {

    class UserDataState(
        override val name: String? = null,
        override val userRecipeStat: User.RecipeStatistic? = null,
        val cookingHistory: List<CookingHistory>? = null,
        val cookingStrike: CookingStrike? = null,
    ) : StatisticsState()

    class RecipesState(
        override val recipes: List<Recipe>? = null,
        override val isRecipesLoading: Boolean = true,
    ) : StatisticsState()

    class ErrorState(override val error: Error) : StatisticsState()

    enum class Error {
        STAT_READ_ERROR,
        RECIPES_READ_ERROR,
    }
}
