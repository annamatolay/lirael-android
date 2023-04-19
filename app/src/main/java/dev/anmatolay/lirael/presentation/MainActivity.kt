package dev.anmatolay.lirael.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.setPadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseActivity
import dev.anmatolay.lirael.databinding.ActivityMainBinding
import dev.anmatolay.lirael.presentation.dialog.ExitConfirmationDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<MainActivityEvent>() {

    var shouldShowExitConfirmationDialog = true
    override val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.uiState.observe { state ->
            shouldShowExitConfirmationDialog = !state.exitConfirmationDialogState.isGone
            setUiMode(state.isDarkModeEnabled)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavigationController()

        binding.bottomNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.statistics_fragment))
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.run {
            setupWithNavController(navController, appBarConfiguration)
            setOnMenuItemClickListener { item ->
                return@setOnMenuItemClickListener when (item.itemId) {
                    R.id.settings_item -> {
                        navController.navigate(MainActivityDirections.actionToSettingsContainerFragment())
                        true
                    }
                    R.id.ui_mode_item -> {
                        setUiMode(!isDarkModeEnable())
                        triggerEvent(MainActivityEvent.UiModeChanged(!isDarkModeEnable()))
                        true
                    }
                    else -> false
                }
            }
        }

        var firstLaunch = true
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash_fragment -> {
                    // Onboarding flow over and popUpTo main_nav_graph, so dropping the "leftover" onboarding_nav_graph
                    // So when user press back (s)he won't try to go back to a fragment that already popped up
                    if (firstLaunch) firstLaunch = false else navController.setGraph(R.navigation.main_nav_graph)
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isStatisticsFragment = navController.isCurrentFragmentLabel(R.string.title_statistics)
                if (shouldShowExitConfirmationDialog && isStatisticsFragment) {
                    ExitConfirmationDialogFragment().show(supportFragmentManager, null)
                } else {
                    isEnabled = false
                    // Because isEnabled passed to mEnabledConsumer it can't process the next back pressing
                    if (isStatisticsFragment) finish() else navController.popBackStack()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        val menuItem = menu?.getItem(0) ?: return super.onCreateOptionsMenu(menu)

        if (isDarkModeEnable()) {
            menuItem.setIcon(R.drawable.ic_toolbar_mode_light)
        } else {
            menuItem.setIcon(R.drawable.ic_toolbar_mode_dark)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavigationController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun makeSnackbar(@StringRes messageResId: Int, length: Int) =
        Snackbar.make(binding.snackbarContainer, messageResId, length)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)

    // Activity.findNavController(viewId: Int) throw IllegalStateException if used with FragmentContainerView
    // This is the official workaround. More info here:  https://issuetracker.google.com/issues/142847973#comment15
    private fun findNavigationController() =
        findNavHostFragment().findNavController()

    private fun findNavHostFragment() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)

    private fun NavController.isCurrentFragmentLabel(@StringRes label: Int) =
        this.currentBackStackEntry?.destination?.label == getString(label)

    private fun setUiMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun isDarkModeEnable() = when (resources.configuration.uiMode) {
        // When you change UI mode in setting and warm start app
        // then config values will be exactly one more than it should be
        // https://issuetracker.google.com/issues/134379747
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_YES + 1 -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_NO + 1 -> false
        else -> false
    }
}
