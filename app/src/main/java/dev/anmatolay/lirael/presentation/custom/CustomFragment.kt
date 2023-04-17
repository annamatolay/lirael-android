package dev.anmatolay.lirael.presentation.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentCustomBinding
import dev.anmatolay.lirael.presentation.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<CustomViewModel>()
    private lateinit var binding: FragmentCustomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentCustomBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
