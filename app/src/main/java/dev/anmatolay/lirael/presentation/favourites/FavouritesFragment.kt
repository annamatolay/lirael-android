package dev.anmatolay.lirael.presentation.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentFavouritesBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.cooking.CookingSummaryFragment
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.setLayoutManagerAndItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : BaseFragment<FavouriteEvent>() {

    override val viewModel by viewModel<FavouritesViewModel>()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentFavouritesBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe { state ->
            with(binding) {
                favouriteRecycleView.isVisible = !state.isLoading
                favouriteProgressBar.isVisible = state.isLoading
            }

            updateRecipes(state.recipes)
            handleError(state.error)
        }
    }

    private fun updateRecipes(recipes: List<Recipe>?) {
        if (recipes?.isEmpty() == true) {
            with(binding) {
                favouriteRecycleView.isVisible = false
                errorImage.isVisible = true
            }
        } else if (recipes != null && recipes.isNotEmpty()) {
            binding.favouriteRecycleView.run {
                setLayoutManagerAndItemDecoration(LinearLayoutManager.VERTICAL)
                adapter = FavouriteRecipeAdapter(recipes, { triggerEvent(FavouriteEvent.OnDeleteClicked(it)) }) {
                    CookingSummaryFragment(it).show(childFragmentManager, null)
                }
            }
        }
    }

    private fun handleError(error: FavouriteState.Error?) {
        if (error != null) {
            binding.favouriteRecycleView.isVisible = false
            binding.errorImage.isVisible = true
        }

        when (error) {
            FavouriteState.Error.DB_READ_ERROR ->
                mainActivity().makeSnackbar(R.string.favourite_read_error, Snackbar.LENGTH_LONG)
            FavouriteState.Error.DB_DELETE_ERROR ->
                mainActivity().makeSnackbar(R.string.favourite_delete_error, Snackbar.LENGTH_LONG)
            null -> {
                // Do nothing
            }
        }
    }
}
