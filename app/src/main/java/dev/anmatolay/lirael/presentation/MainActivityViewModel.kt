package dev.anmatolay.lirael.presentation

import android.content.res.Resources
import androidx.annotation.RawRes
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.dto.PresetRecipeDto
import dev.anmatolay.lirael.domain.model.PresetRecipe
import dev.anmatolay.lirael.domain.usecase.recipe.PopulatePresetRecipesUseCase
import dev.anmatolay.lirael.presentation.dialog.exit.ExitConfirmationDialogState
import dev.anmatolay.lirael.util.Constants
import io.reactivex.rxjava3.core.Completable
import okio.Buffer
import timber.log.Timber

class MainActivityViewModel(
    private val sharedPrefHandler: SharedPrefHandler,
    private val resources: Resources,
    private val schedulerProvider: SchedulerProvider,
    private val moshi: Moshi,
    private val populatePresetRecipesUseCase: PopulatePresetRecipesUseCase,
) : BaseUdfViewModel<MainActivityState, MainActivityEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        val uiModePrefKey = resources.getString(R.string.key_pref_ui_mode)
        val isGone = sharedPrefHandler.getBoolean(Constants.KEY_HIDE_EXIT_DIALOG)
        val isDarkModeEnabled = sharedPrefHandler.getBoolean(uiModePrefKey)
        triggerUiStateChange(
            MainActivityState(ExitConfirmationDialogState(isGone), isDarkModeEnabled)
        )

        doOnUiEventReceived { event ->
            when (event) {
                is MainActivityEvent.UiModeChanged ->
                    sharedPrefHandler.putBoolean(uiModePrefKey, event.isDarkModeEnabled)
            }
        }.subscribe().disposeOnDestroy()

        populatePresetRecipesUseCase.isEmpty()
            .observeOn(schedulerProvider.mainThread())
            .flatMapCompletable { isEmpty ->
                if (isEmpty) {
                    Timber.tag(DB_LOG_TAG).i("POPULATE DATABASE STARTED")
                    val data = readPresetRecipesFromJson()
                    populatePresetRecipesUseCase(data)
                        .doOnComplete { Timber.tag(DB_LOG_TAG).i("POPULATE DATABASE COMPLETED") }
                } else {
                    Completable.complete()
                }
            }
            .subscribe(
                { Timber.tag(DB_LOG_TAG).i("DATABASE POPULATED") },
                { Timber.tag(DB_LOG_TAG).e(it) }
            )
            .disposeOnDestroy()
    }

    private fun readPresetRecipesFromJson(): List<PresetRecipe> {
        val data = mutableListOf<PresetRecipe>()

        PresetRecipe.Category.values().forEachIndexed { index, category ->
            data.addAll(readFromJson(rawResList[index], category))
        }

        return data.toList()
    }

    private fun readFromJson(@RawRes id: Int, category: PresetRecipe.Category): List<PresetRecipe> {
        val inputStream = resources.openRawResource(id)
        val type = Types.newParameterizedType(List::class.java, PresetRecipeDto::class.java)
        val dto = moshi.adapter<List<PresetRecipeDto>>(type).fromJson(Buffer().readFrom(inputStream))
        val data = mutableListOf<PresetRecipe>()
        dto!!.forEach { data.add(it.toModel(category)) }
        return data.toList()
    }

    companion object {
        private const val DB_LOG_TAG = "PresetRecipe DB"

        private val rawResList = listOf(
            R.raw.recipe_preset_european,
            R.raw.recipe_preset_italian,
            R.raw.recipe_preset_french,
            R.raw.recipe_preset_american,
            R.raw.recipe_preset_asian,
            R.raw.recipe_preset_turkish,
            R.raw.recipe_preset_cakes,
            R.raw.recipe_preset_cocktails,
            R.raw.recipe_preset_liquors,
        )
    }
}
