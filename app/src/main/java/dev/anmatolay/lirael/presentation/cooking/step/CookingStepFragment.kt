package dev.anmatolay.lirael.presentation.cooking.step

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentCookingStepBinding
import dev.anmatolay.lirael.presentation.cooking.step.CookingStepViewPagerFragment.Companion.currentImages
import dev.anmatolay.lirael.presentation.cooking.step.CookingStepViewPagerFragment.Companion.images
import dev.anmatolay.lirael.util.Constants.KEY_RECIPE_ADAPTER_ITEM
import dev.anmatolay.lirael.util.extension.getRecipeItemParcelable
import dev.anmatolay.lirael.util.extension.mainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class CookingStepFragment @JvmOverloads constructor(
    private var recipeItem: RecipeAdapterItem? = null,
    private val dismiss: () -> Unit = {},
    private val isViewPagerUserInputEnabled: (Boolean) -> Unit
) : BaseFragment<CookingStepEvent>() {

    init {
        arguments = Bundle(1).apply { putParcelable(KEY_RECIPE_ADAPTER_ITEM, recipeItem) }
    }

    override val viewModel by viewModel<CookingStepViewModel>()
    private lateinit var binding: FragmentCookingStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentCookingStepBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeItem = arguments?.getRecipeItemParcelable()

        binding.setUpLayout()

        viewModel.uiState.observe { state ->
            with(binding) {
                positiveProgressBar.isVisible = state.isPositiveLoading
                neutralProgressBar.isVisible = state.isNeutralLoading
                handleButtonsAndViewPagerInput(state)

                if (state.isUpdateDone) {
                    dismiss.invoke()
                }

                when (state.error) {
                    CookingStepState.Error.USER_STAT_UPDATE_FAILED ->
                        mainActivity().makeSnackbar(R.string.stat_read_error, Snackbar.LENGTH_LONG)
                    CookingStepState.Error.RECIPE_DB_CREATE_ERROR ->
                        mainActivity().makeSnackbar(R.string.favourite_save_error, Snackbar.LENGTH_LONG)
                    null -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_RECIPE_ADAPTER_ITEM, recipeItem)
    }


    private fun pickRandomAndRemoveAt(): Int {
        val index = Random.nextInt(currentImages.lastIndex)
        return currentImages.removeAt(index)
    }

    private fun FragmentCookingStepBinding.setUpLayout() {
        with(this) {
            val currentStep = recipeItem?.step?.plus(1) ?: 0
            val allStep = recipeItem?.instructionsLastIndex?.plus(1) ?: 0
            step.text = getString(R.string.cooking_step, currentStep, allStep)
            title.text = recipeItem?.title ?: getString(R.string.cooking_step_recipe_not_found)
            instruction.text = recipeItem?.instruction ?: getString(R.string.cooking_step_instruction_not_found)
            val pickedImage =
                if (currentImages.lastIndex > 0) {
                    pickRandomAndRemoveAt()
                } else {
                    currentImages.addAll(images)
                    pickRandomAndRemoveAt()
                }
            image.load(
                if (recipeItem == null)
                    R.drawable.cat_error_not_found
                else
                    pickedImage
            )
            negativeButton.setOnClickListener { dismiss.invoke() }
            positiveButton.isVisible = recipeItem?.isLastItem == true
            positiveButton.setOnClickListener {
                handleOnClick { triggerEvent(CookingStepEvent.OnPositiveButtonClicked(mainActivity().recipe)) }
            }
            neutralButton.isVisible = recipeItem?.isLastItem == true
            neutralButton.setOnClickListener {
                handleOnClick { triggerEvent(CookingStepEvent.OnNeutralButtonClicked) }
            }
        }
    }

    private fun handleOnClick(doOnClick: () -> Unit) {
        binding.positiveButton.isEnabled = false
        binding.neutralButton.isEnabled = false
        binding.negativeButton.isEnabled = false
        isViewPagerUserInputEnabled.invoke(false)

        with(binding.confetti) {
            isVisible = true
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {

                override fun onAnimationEnd(animation: Animator) {
                    doOnClick.invoke()
                }

                override fun onAnimationCancel(animation: Animator) {
                    binding.positiveButton.isEnabled = true
                    binding.negativeButton.isEnabled = true
                    isViewPagerUserInputEnabled.invoke(true)
                }

                override fun onAnimationStart(animation: Animator) {
                    // Do nothing
                }

                override fun onAnimationRepeat(animation: Animator) {
                    // Do nothing
                }
            })
        }
    }

    private fun FragmentCookingStepBinding.handleButtonsAndViewPagerInput(state: CookingStepState) {
        if (state.isPositiveLoading) {
            positiveButton.isVisible = false
            neutralButton.isEnabled = false
            negativeButton.isEnabled = false
            isViewPagerUserInputEnabled.invoke(false)
        } else if (state.isNeutralLoading) {
            neutralButton.isVisible = false
            positiveButton.isEnabled = false
            negativeButton.isEnabled = false
            isViewPagerUserInputEnabled.invoke(false)
        } else {
            positiveButton.isVisible = true
            neutralButton.isVisible = true
            positiveButton.isEnabled = true
            neutralButton.isEnabled = true
            negativeButton.isEnabled = true
            isViewPagerUserInputEnabled.invoke(true)
        }
    }
}
