package dev.anmatolay.lirael.presentation.recipes

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.usecase.recipe.GetPresetRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.SearchRecipesUseCase
import dev.anmatolay.lirael.presentation.recipes.RecipesState.Error.API_ERROR
import dev.anmatolay.lirael.presentation.recipes.RecipesState.Error.NOT_FOUND
import timber.log.Timber

class RecipesViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val getPresetRecipesUseCase: GetPresetRecipesUseCase,
) : BaseUdfViewModel<RecipesState, RecipesEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { event ->
            when (event) {
                is RecipesEvent.SearchRecipe -> {
                    triggerUiStateChange(RecipesState(isRecipesLoading = true))

                    searchRecipesUseCase(event.keyword)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { recipesList ->
                                if (recipesList.isNotEmpty())
                                    triggerUiStateChange(RecipesState(recipesList, isRecipesLoading = false))
                                else
                                    triggerUiStateChange(RecipesState(isRecipesLoading = false, error = NOT_FOUND))
                            },
                            {
                                Timber.e(it)
                                triggerUiStateChange(RecipesState(isRecipesLoading = false, error = API_ERROR))
                            }
                        ).disposeOnPause()
                }
                is RecipesEvent.PresetOnClicked -> {
                    triggerUiStateChange(RecipesState(isRecipesLoading = true))

                    getPresetRecipesUseCase(event.category)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { presetRecipesList ->
                                val recipesList = mutableListOf<Recipe>()
                                presetRecipesList.forEach { recipesList.add(it.toRecipe()) }
                                if (recipesList.isNotEmpty())
                                    triggerUiStateChange(RecipesState(recipesList, isRecipesLoading = false))
                                else
                                    triggerUiStateChange(RecipesState(isRecipesLoading = false, error = NOT_FOUND))
                            },
                            {
                                Timber.e(it)
                                triggerUiStateChange(RecipesState(isRecipesLoading = false, error = API_ERROR))
                            }
                        ).disposeOnPause()
                }
            }
        }.subscribe().disposeOnPause()
    }

}
