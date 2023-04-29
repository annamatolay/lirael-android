package dev.anmatolay.lirael.presentation.cooking

import androidx.room.rxjava3.EmptyResultSetException
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.DeleteFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.GetFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.SaveFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.cooking.CookingSummaryState.Error.*
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class CookingSummaryViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getFavouriteRecipeUseCase: GetFavouriteRecipeUseCase,
    private val saveFavouriteRecipeUseCase: SaveFavouriteRecipeUseCase,
    private val deleteFavouriteRecipeUseCase: DeleteFavouriteRecipeUseCase,
) : BaseUdfViewModel<CookingSummaryState, CookingSummaryEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        getUserUseCase()
            .observeOn(schedulerProvider.mainThread())
            .map { it.copy(recipeStatistic = it.recipeStatistic.copy(opened = it.recipeStatistic.opened + 1)) }
            .flatMapCompletable { updateUserUseCase(it) }
            .subscribe(
                { Timber.d("Recipe stats updated") },
                { Timber.e(it) },
            )
            .disposeOnDestroy()

        doOnUiEventReceived { event ->
            when (event) {
                is CookingSummaryEvent.CheckFavouriteSaved -> {
                    triggerUiStateChange(CookingSummaryState(isLoading = true))
                    getFavouriteRecipeUseCase(event.title)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(CookingSummaryState(isLoading = false, isSaved = true)) },
                            { throwable ->
                                if (throwable is EmptyResultSetException) {
                                    triggerUiStateChange(CookingSummaryState(isLoading = false, isSaved = false))
                                } else {
                                    Timber.e(throwable)
                                    triggerUiStateChange(CookingSummaryState(isLoading = false, error = DB_READ_ERROR))
                                }
                            }
                        )
                        .disposeOnDestroy()
                }
            }
        }.subscribe().disposeOnDestroy()
    }

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { event ->
            when (event) {
                is CookingSummaryEvent.FavouriteOnClicked -> {
                    triggerUiStateChange(CookingSummaryState(isLoading = true))

                    if (event.isDeletion) {
                        deleteFavouriteRecipeUseCase(event.recipe)
                            .observeOn(schedulerProvider.mainThread())
                            .andThen(updateRecipeSavedStat(-1))
                            .subscribe(
                                { triggerUiStateChange(CookingSummaryState(isLoading = false, isSaved = false)) },
                                {
                                    Timber.e(it)
                                    triggerUiStateChange(
                                        CookingSummaryState(
                                            isLoading = false,
                                            error = DB_DELETE_ERROR
                                        )
                                    )
                                }
                            )
                            .disposeOnPause()
                    } else {
                        saveFavouriteRecipeUseCase(event.recipe)
                            .observeOn(schedulerProvider.mainThread())
                            .andThen(updateRecipeSavedStat(1))
                            .subscribe(
                                { triggerUiStateChange(CookingSummaryState(isLoading = false, isSaved = true)) },
                                {
                                    Timber.e(it)
                                    triggerUiStateChange(
                                        CookingSummaryState(
                                            isLoading = false,
                                            error = DB_CREATE_ERROR
                                        )
                                    )
                                }
                            )
                            .disposeOnPause()
                    }
                }
            }
        }.subscribe().disposeOnPause()
    }

    private fun updateRecipeSavedStat(modifier: Int): Completable =
        getUserUseCase()
            .map { it.copy(recipeStatistic = it.recipeStatistic.copy(saved = it.recipeStatistic.saved + modifier)) }
            .flatMapCompletable { updateUserUseCase(it) }
}
