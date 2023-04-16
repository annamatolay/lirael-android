package dev.anmatolay.lirael.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.databinding.FragmentHomeBinding
import dev.anmatolay.lirael.databinding.FragmentNotificationsBinding
import dev.anmatolay.lirael.presentation.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<NotificationsViewModel>()
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNotificationsBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
