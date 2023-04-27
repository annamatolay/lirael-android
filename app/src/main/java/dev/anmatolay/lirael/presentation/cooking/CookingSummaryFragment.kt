package dev.anmatolay.lirael.presentation.cooking

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    val function: () -> Unit = {},
) : BaseBottomSheetDialogFragment<CookingEvent>() {

    override val viewModel by viewModel<CookingSummaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (recipe == null)
            recipe = savedInstanceState?.getRecipeParcelable()

        return FragmentCookingSummaryBinding.inflate(inflater, container, false)
            .apply {
                if (recipe == null) {
                    title.text = getString(R.string.cooking_summary_error)
                    summaryContainer.isVisible = false
                    errorImage.isVisible = true
                } else {
                    title.text = recipe!!.title
                    ingredientsList.apply {
                        adapter =
                            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, recipe!!.ingredients)
                    }
                    button.setOnClickListener {
                        dismiss()
                        navigateTo(MainNavGraphDirections.actionToCookingStepFragment(recipe!!))
                    }
                }
            }
            .root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPENED_RECIPE, recipe)
    }

    override fun onDismiss(dialog: DialogInterface) {
        function.invoke()
        super.onDismiss(dialog)
    }
}
