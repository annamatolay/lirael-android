package dev.anmatolay.lirael.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseActivity
import dev.anmatolay.lirael.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<Event>() {

    override val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavigationController()

        binding.bottomNavView.setupWithNavController(navController)
        //        binding.toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
    }

    // Activity.findNavController(viewId: Int) throw IllegalStateException if used with FragmentContainerView
    // This is the official workaround. More info here:  https://issuetracker.google.com/issues/142847973#comment15
    private fun findNavigationController() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
}
