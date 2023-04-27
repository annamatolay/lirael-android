package dev.anmatolay.lirael.presentation.cooking.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.FragmentCookingStepviewPagerBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.popBackStack


class CookingStepViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentCookingStepviewPagerBinding
    private val args: CookingStepViewPagerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentCookingStepviewPagerBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCookingFlow(args.recipe)

        binding.viewPager[binding.viewPager.currentItem]
        val page: Fragment? =
            mainActivity().supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + binding.viewPager.getCurrentItem())

        //        binding.viewPager.isUserInputEnabled = false

        with(activity) {
            this?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
                with(binding) {
                    if (viewPager.isVisible) {
                        if (viewPager.currentItem != 0) {
                            viewPager.currentItem = viewPager.currentItem - 1
                        } else {
                            popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun startCookingFlow(recipe: Recipe) {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
                super.onBindViewHolder(holder, position, payloads)
            }

            override fun getItemCount(): Int = recipe.instructions.size

            override fun createFragment(position: Int): CookingStepFragment {
                return CookingStepFragment(recipe.toRecipeItem(position), { popBackStack() }) { isEnabled ->
                    binding.viewPager.isUserInputEnabled = isEnabled
                }
            }
        }
    }

    companion object {
        val images = listOf(
            R.drawable.chefcat_1,
            R.drawable.chefcat_2,
            R.drawable.chefcat_3,
            R.drawable.chefcat_4,
        )

        val currentImages = mutableListOf(
            R.drawable.chefcat_1,
            R.drawable.chefcat_2,
            R.drawable.chefcat_3,
            R.drawable.chefcat_4,
        )
    }
}
