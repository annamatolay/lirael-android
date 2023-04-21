package dev.anmatolay.lirael.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentStatisticsBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.extension.mainActivity
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
            // FIXME NullPointerException when change ui mode
            findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.VISIBLE
            findViewById<View>(R.id.settings_item)?.isInvisible = false
            findViewById<View>(R.id.ui_mode_item)?.isInvisible = false
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.uiState.observe { state ->
            updateName(state.name)

            updateRecipeStatistics(state.userRecipeStat)

            showErrorSnackbarIfErrorNotNull(state.error)

            val data = listOf(
                Recipe("Spaghetti", listOf(), listOf(), ""),
                Recipe("Paste", listOf(), listOf(), ""),
                Recipe("Something very long tittle with nothing meaningful", listOf(), listOf(), "")
            )
            binding.randomRecipesRecycleView.run {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = RandomRecipeAdapter(data)
                addItemDecoration(
                    DividerItemDecoration(
                        this.context,
                        LinearLayoutManager.HORIZONTAL
                    ).apply {
                        setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.item_divider, null)!!)
                    }
                )
            }
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

    private fun showErrorSnackbarIfErrorNotNull(error: StatisticsState.Error?) {
        if (error != null)
            when (error) {
                StatisticsState.Error.STAT_READ_ERROR ->
                    mainActivity().makeErrorSnackbar(R.string.stat_read_error) {
                        triggerEvent(StatisticsEvent.RetryGetStatOnClicked)
                    }
                        .show()
            }
    }
}
