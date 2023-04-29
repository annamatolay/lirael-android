package dev.anmatolay.lirael.presentation.cooking

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import dev.anmatolay.lirael.MainNavGraphDirections
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseBottomSheetDialogFragment
import dev.anmatolay.lirael.databinding.FragmentCookingSummaryBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.Constants.KEY_OPENED_RECIPE
import dev.anmatolay.lirael.util.extension.getRecipeParcelable
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel


class CookingSummaryFragment
@JvmOverloads constructor(
    private var recipe: Recipe? = null,
    private val doOnDismiss: () -> Unit = {},
) : BaseBottomSheetDialogFragment<CookingSummaryEvent>() {

    override val viewModel by viewModel<CookingSummaryViewModel>()
    private lateinit var binding: FragmentCookingSummaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (recipe == null)
            recipe = savedInstanceState?.getRecipeParcelable()

        return FragmentCookingSummaryBinding.inflate(inflater, container, false)
            .apply {
                if (recipe == null) {
                    title.text = getString(R.string.cooking_summary_error)
                    summaryContainer.isVisible = false
                    errorImage.isVisible = true
                    favouriteButton.setImageResource(R.drawable.ic_action_favorite_add)
                    favouriteButton.isEnabled = false
                } else {
                    title.text = recipe!!.title
                    ingredientsList.apply {
                        adapter =
                            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, recipe!!.ingredients)
                    }
                    startCookingFlowButton.setOnClickListener {
                        dismiss()
                        navigateTo(MainNavGraphDirections.actionToCookingStepFragment(recipe!!))
                    }
                }
                binding = this
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triggerEvent(CookingSummaryEvent.CheckFavouriteSaved(recipe!!.title))

        viewModel.uiState.observe { state ->
            with(binding) {
                loading.isVisible = state.isLoading
                favouriteButton.isVisible = !state.isLoading

                if (state.isSaved != null) {
                    with(favouriteButton) {
                        if (state.isSaved) {
                            setImageResource(R.drawable.ic_action_favorite_remove)
                            setOnClickListener {
                                triggerEvent(CookingSummaryEvent.FavouriteOnClicked(recipe!!, isDeletion = true))
                            }
                        } else {
                            setImageResource(R.drawable.ic_action_favorite_add)
                            setOnClickListener {
                                triggerEvent(CookingSummaryEvent.FavouriteOnClicked(recipe!!, isDeletion = false))
                            }
                        }
                    }
                }
            }

            if (state.error != null) {
                when (state.error) {
                    CookingSummaryState.Error.DB_READ_ERROR ->
                        mainActivity().makeSnackbar(R.string.favourite_read_error, Toast.LENGTH_LONG)
                    CookingSummaryState.Error.DB_CREATE_ERROR ->
                        mainActivity().makeSnackbar(R.string.favourite_save_error, Toast.LENGTH_LONG)
                    CookingSummaryState.Error.DB_DELETE_ERROR ->
                        mainActivity().makeSnackbar(R.string.favourite_delete_error, Toast.LENGTH_LONG)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPENED_RECIPE, recipe)
    }

    override fun onDismiss(dialog: DialogInterface) {
        doOnDismiss.invoke()
        super.onDismiss(dialog)
    }
}
