package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.SaveFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.cooking.step.CookingStepState.Error.RECIPE_DB_CREATE_ERROR
import dev.anmatolay.lirael.presentation.cooking.step.CookingStepState.Error.USER_STAT_UPDATE_FAILED
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

class CookingStepViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val saveFavouriteRecipeUseCase: SaveFavouriteRecipeUseCase,
) : BaseUdfViewModel<CookingStepState, CookingStepEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        doOnUiEventReceived { event ->
            when (event) {
                is CookingStepEvent.OnPositiveButtonClicked -> {
                    triggerUiStateChange(CookingStepState(isPositiveLoading = true))

                    if (event.recipe == null) {
                        triggerUiStateChange(CookingStepState(error = RECIPE_DB_CREATE_ERROR))
                        Timber.e(IllegalStateException("Recipe is NULL. Can not be saved."))
                    } else {
                        saveFavouriteRecipeUseCase(event.recipe)
                            .observeOn(schedulerProvider.mainThread())
                            .andThen(updateUserUseCase.updateRecipeSavedStat(1))
                            .doOnError {
                                Timber.e(it)
                                triggerUiStateChange(
                                    CookingStepState(
                                        isNeutralLoading = false,
                                        error = USER_STAT_UPDATE_FAILED
                                    )
                                )
                            }
                            .andThen(getUserUseCase())
                            .updateCookedStat()
                            .subscribe(
                                {
                                    triggerUiStateChange(
                                        CookingStepState(
                                            isPositiveLoading = false,
                                            isUpdateDone = true,
                                        )
                                    )
                                },
                                {
                                    Timber.e(it)
                                    triggerUiStateChange(
                                        CookingStepState(
                                            isPositiveLoading = false,
                                            error = USER_STAT_UPDATE_FAILED,
                                        )
                                    )
                                }
                            )
                            .disposeOnPause()

                    }
                }
                CookingStepEvent.OnNeutralButtonClicked -> {
                    triggerUiStateChange(CookingStepState(isNeutralLoading = true))

                    getUserUseCase()
                        .observeOn(schedulerProvider.mainThread())
                        .updateCookedStat()
                        .subscribe(
                            { triggerUiStateChange(CookingStepState(isNeutralLoading = false, isUpdateDone = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(
                                    CookingStepState(
                                        isNeutralLoading = false,
                                        error = USER_STAT_UPDATE_FAILED,
                                    )
                                )
                            }
                        )
                        .disposeOnPause()
                }
            }
        }.subscribe().disposeOnPause()
    }

    private fun Single<User>.updateCookedStat(): Completable = this
        .map { it.copy(recipeStatistic = it.recipeStatistic.copy(cooked = it.recipeStatistic.cooked + 1)) }
        .flatMapCompletable { updateUserUseCase(it) }
}
