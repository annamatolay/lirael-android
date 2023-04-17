package dev.anmatolay.lirael.presentation.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentFavouritesBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<CustomViewModel>()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentFavouritesBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
