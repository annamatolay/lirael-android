package dev.anmatolay.lirael.presentation.cooking

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseBottomSheetDialogFragment
import dev.anmatolay.lirael.databinding.FragmentCookingSummaryBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.Constants.KEY_OPENED_RECIPE
import org.koin.androidx.viewmodel.ext.android.viewModel


class CookingSummaryFragment
@JvmOverloads constructor(
    private var recipe: Recipe? = null,
) : BaseBottomSheetDialogFragment<CookingEvent>() {

    override val viewModel by viewModel<CookingSummaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("DEPRECATION")
        if (recipe == null)
            recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                savedInstanceState?.getParcelable(KEY_OPENED_RECIPE, Recipe::class.java)
            else
                savedInstanceState?.getParcelable(KEY_OPENED_RECIPE)

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
                }
            }
            .root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPENED_RECIPE, recipe)
    }
}
