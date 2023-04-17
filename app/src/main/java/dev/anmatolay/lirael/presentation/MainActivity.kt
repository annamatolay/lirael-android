package dev.anmatolay.lirael.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseActivity
import dev.anmatolay.lirael.databinding.ActivityMainBinding
import dev.anmatolay.lirael.presentation.dialog.ExitConfirmationDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<Event>() {

    override val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    var shouldShowExitConfirmationDialog = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.uiState.observe { state ->
            shouldShowExitConfirmationDialog = !state.exitConfirmationDialogState.isGone

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavigationController()

        binding.bottomNavView.setupWithNavController(navController)
        //        binding.toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldShowExitConfirmationDialog)
                    ExitConfirmationDialogFragment().show(supportFragmentManager, null)
                else
                    finish()
            }
        })

    }

    // Activity.findNavController(viewId: Int) throw IllegalStateException if used with FragmentContainerView
    // This is the official workaround. More info here:  https://issuetracker.google.com/issues/142847973#comment15
    private fun findNavigationController() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
}
