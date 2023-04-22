package dev.anmatolay.lirael.presentation.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentRecipesBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.statistics.RandomRecipeAdapter
import dev.anmatolay.lirael.util.extension.isValid
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.setLayoutManagerAndItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipesFragment : BaseFragment<RecipesEvent>() {

    override val viewModel by viewModel<RecipesViewModel>()
    private lateinit var binding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentRecipesBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onResume() {
        super.onResume()

        setupTextInput()

        viewModel.uiState.observe { state ->
            binding.recipesRecycleView.isVisible = !state.isRecipesLoading

            updateRecipes(state.recipes)
            handleError(state.error)
        }
    }

    private fun setupTextInput() {
        with(binding) {
            textInputEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    closeKeyboard()
                    searchButton.callOnClick()
                    return@OnEditorActionListener true
                }
                false
            })
            with(textInputLayout) {
                searchButton.setOnClickListener {
                    closeKeyboard()
                    if (editText?.text?.isValid() == false) {
                        error = getString(R.string.input_text_error)
                    } else {
                        error = null
                        binding.recipesProgressBar.isVisible = true
                        triggerEvent(RecipesEvent.SearchRecipe(editText!!.text.toString()))
                    }
                }
            }
        }
    }

    private fun updateRecipes(recipes: List<Recipe>?) {
        if (recipes != null) {
            binding.recipesRecycleView.run {
                setLayoutManagerAndItemDecoration(LinearLayoutManager.VERTICAL)
                adapter = FoundRecipeAdapter(recipes)
            }
            binding.recipesProgressBar.isVisible = false
        }
    }

    private fun handleError(error: RecipesState.Error?) {
        if (error != null) {
            binding.recipesProgressBar.isVisible = false
            with(binding.errorImage) {
                isVisible = true
                when (error) {
                    RecipesState.Error.NOT_FOUND -> load(R.drawable.cat_error_not_found)
                    RecipesState.Error.API_ERROR -> load(R.drawable.cat_error_generic)
                    RecipesState.Error.DB_ERROR -> load(R.drawable.cat_error_generic)
                }
            }
            with(mainActivity()) {
                if (error == RecipesState.Error.NOT_FOUND) {
                    makeSnackbar(R.string.recipes_not_found_error, Snackbar.LENGTH_LONG).show()
                } else {
                    makeSnackbar(R.string.recipes_read_error, Snackbar.LENGTH_LONG).show()
                }
            }
        } else {
            binding.errorImage.isVisible = false
        }
    }

    private fun closeKeyboard() {
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
