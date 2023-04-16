package dev.anmatolay.lirael.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentDashboardBinding
import dev.anmatolay.lirael.databinding.FragmentNotificationsBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<NotificationsViewModel>()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentDashboardBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
