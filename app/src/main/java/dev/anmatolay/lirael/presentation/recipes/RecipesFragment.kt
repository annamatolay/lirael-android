package dev.anmatolay.lirael.presentation.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentRecipesBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<CustomViewModel>()
    private lateinit var binding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentRecipesBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
