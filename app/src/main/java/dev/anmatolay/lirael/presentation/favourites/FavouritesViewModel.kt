package dev.anmatolay.lirael.presentation.favourites

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.DeleteFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.GetAllFavouriteRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.favourites.FavouriteState.Error.DB_DELETE_ERROR
import dev.anmatolay.lirael.presentation.favourites.FavouriteState.Error.DB_READ_ERROR
import timber.log.Timber

class FavouritesViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getAllFavouriteRecipesUseCase: GetAllFavouriteRecipesUseCase,
    private val deleteFavouriteRecipeUseCase: DeleteFavouriteRecipeUseCase,
) : BaseUdfViewModel<FavouriteState, FavouriteEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        triggerUiStateChange(FavouriteState(isLoading = true))

        getAllFavouriteRecipesUseCase()
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                { triggerUiStateChange(FavouriteState(isLoading = false, recipes = it)) },
                { triggerUiStateChange(FavouriteState(isLoading = false, error = DB_READ_ERROR)) }
            )
            .disposeOnDestroy()

        doOnUiEventReceived { event ->
            when (event) {
                is FavouriteEvent.OnDeleteClicked -> {
                    triggerUiStateChange(FavouriteState(isLoading = true))

                    deleteFavouriteRecipeUseCase(event.recipe)
                        .observeOn(schedulerProvider.mainThread())
                        .andThen(updateUserUseCase.updateRecipeSavedStat(-1))
                        .andThen(getAllFavouriteRecipesUseCase())
                        .subscribe(
                            { triggerUiStateChange(FavouriteState(isLoading = false, recipes = it)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(
                                    FavouriteState(
                                        isLoading = false,
                                        error = DB_DELETE_ERROR
                                    )
                                )
                            }
                        )
                        .disposeOnDestroy()
                }
            }
        }.subscribe().disposeOnDestroy()
    }
}
