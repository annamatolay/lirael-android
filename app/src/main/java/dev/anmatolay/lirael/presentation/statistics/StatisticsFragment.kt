package dev.anmatolay.lirael.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentStatisticsBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.setLayoutManagerAndItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : BaseFragment<StatisticsEvent>() {

    override val viewModel by viewModel<StatisticsViewModel>()
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentStatisticsBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.run {
            findViewById<AppBarLayout>(R.id.app_bar)?.visibility = View.VISIBLE
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()

        triggerEvent(StatisticsEvent.GetRandomRecipes)

        viewModel.uiState.observe { state ->
            when (state) {
                is StatisticsState.UserDataState -> {
                    updateName(state.name)
                    updateRecipeStatistics(state.userRecipeStat)
                }
                is StatisticsState.RecipesState -> updateRecipes(state.recipes)
                is StatisticsState.ErrorState -> showErrorSnackbarIfErrorNotNull(state.error)
            }

            binding.recipesRecycleView.isVisible = !state.isRecipesLoading
        }
    }

    private fun updateName(data: String?) {
        val name = data ?: getString(R.string.user_default_name)
        binding.greeting.text = getString(R.string.stat_greetings, name)
    }

    private fun updateRecipeStatistics(userRecipeStat: User.RecipeStatistic?) {
        if (userRecipeStat != null)
            binding.layoutRecipeStats.run {
                numberCooked.text = userRecipeStat.cooked.toString()
                numberSaved.text = userRecipeStat.saved.toString()
                numberCreated.text = userRecipeStat.created.toString()
            }
        else
            binding.layoutRecipeStats.run {
                numberCooked.text = "?"
                numberSaved.text = "?"
                numberCreated.text = "?"
            }
    }

    private fun updateRecipes(recipes: List<Recipe>?) {
        if (recipes != null) {
            binding.recipesRecycleView.run {
                setLayoutManagerAndItemDecoration(LinearLayoutManager.HORIZONTAL)
                adapter = RandomRecipeAdapter(recipes)
            }
            binding.recipeProgressBar.isVisible = false
        }
    }

    private fun showErrorSnackbarIfErrorNotNull(error: StatisticsState.Error?) {
        if (error != null)
            with(mainActivity()) {
                when (error) {
                    StatisticsState.Error.STAT_READ_ERROR ->
                        makeErrorSnackbar(R.string.stat_read_error) {
                            triggerEvent(StatisticsEvent.RetryGetStatOnClicked)
                        }.show()
                    StatisticsState.Error.RECIPES_READ_ERROR ->
                        makeErrorSnackbar(R.string.recipes_read_error) {
                            triggerEvent(StatisticsEvent.RetryGetRandomRecipes)
                        }.show()
                }
            }
    }
}
