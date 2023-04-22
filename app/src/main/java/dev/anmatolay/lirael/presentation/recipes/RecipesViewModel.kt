package dev.anmatolay.lirael.presentation.recipes

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.GetRecipesUseCase
import dev.anmatolay.lirael.presentation.recipes.RecipesState.Error.API_ERROR
import dev.anmatolay.lirael.presentation.recipes.RecipesState.Error.NOT_FOUND
import timber.log.Timber

class RecipesViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getRecipesUseCase: GetRecipesUseCase,
) : BaseUdfViewModel<RecipesState, RecipesEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { event ->
            when (event) {
                //                is RecipesEvent.GetPreset -> TODO
                is RecipesEvent.SearchRecipe -> {
                    triggerUiStateChange(RecipesState(isRecipesLoading = true))
                    getRecipesUseCase(event.keyword)
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
            }
        }.subscribe().disposeOnPause()
    }

}
