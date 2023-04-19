package dev.anmatolay.lirael.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentStatisticsBinding
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

    override fun onResume() {
        super.onResume()

        viewModel.uiState.observe { state ->
            updateName(state.name)

            updateRecipeStatistics(state.userRecipeStat)

            showErrorSnackbarIfErrorNotNull(state.error)
        }
    }

    private fun updateName(data: String?) {
        val name = data ?: getString(R.string.user_default_name)
        binding.greeting.text = getString(R.string.stat_greetings, name)
    }

    private fun updateRecipeStatistics(userRecipeStat: User.RecipeStatistic?) {
        if (userRecipeStat != null)
            binding.run {
                numberCooked.text = userRecipeStat.cooked.toString()
                numberSaved.text = userRecipeStat.saved.toString()
                numberCreated.text = userRecipeStat.created.toString()
            }
        else
            binding.run {
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
