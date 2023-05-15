package dev.anmatolay.lirael.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseActivity
import dev.anmatolay.lirael.databinding.ActivityMainBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.dialog.exit.ExitConfirmationDialogFragment
import dev.anmatolay.lirael.util.extension.isDarkModeEnable
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<MainActivityEvent>() {

    var shouldShowExitConfirmationDialog = true
    var recipe: Recipe? = null
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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.statistics_fragment), binding.drawerLayout)
        binding.bottomNavView.setupWithNavController(navController)
        binding.navigation.setupWithNavController(navController)
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
                        setUiMode(!resources.isDarkModeEnable())
                        triggerEvent(MainActivityEvent.UiModeChanged(!resources.isDarkModeEnable()))
                        true
                    }
                    else -> false
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isStatisticsFragment = navController.isCurrentFragmentLabel(R.string.title_statistics)
                val isWelcomeFragment = navController.isCurrentFragmentLabel(R.string.onboarding_welcome_title)
                if (shouldShowExitConfirmationDialog && isStatisticsFragment) {
                    ExitConfirmationDialogFragment().show(supportFragmentManager, null)
                } else {
                    if (isStatisticsFragment || isWelcomeFragment) finish() else navController.popBackStack()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        val menuItem = menu?.getItem(0) ?: return super.onCreateOptionsMenu(menu)

        if (resources.isDarkModeEnable()) {
            menuItem.setIcon(R.drawable.ic_toolbar_mode_light)
        } else {
            menuItem.setIcon(R.drawable.ic_toolbar_mode_dark)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavigationController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun makeSnackbar(@StringRes messageResId: Int, length: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(binding.snackbarContainer, messageResId, length)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)

    fun makeErrorSnackbar(@StringRes messageResId: Int, length: Int = Snackbar.LENGTH_LONG, onRetry: () -> Unit) =
        makeSnackbar(messageResId, length)
            .setAction(R.string.snackbar_error_retry) { onRetry.invoke() }

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
}
