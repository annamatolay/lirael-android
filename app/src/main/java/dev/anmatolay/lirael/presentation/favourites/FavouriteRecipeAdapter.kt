package dev.anmatolay.lirael.presentation.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutItemFavouriteRecipesBinding
import dev.anmatolay.lirael.domain.model.Recipe

class FavouriteRecipeAdapter(
    private val dataSet: List<Recipe>,
    private val doOnDeleteClick: (Recipe) -> Unit,
    private val doOnClick: (Recipe) -> Unit,
) :
    RecyclerView.Adapter<FavouriteRecipeAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemFavouriteRecipesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemFavouriteRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        val context = holder.itemView.context
        with(holder) {
            binding.title.text = recipe.title
            binding.ingredientsCount.text =
                context.getString(R.string.recipe_card_ingredients, recipe.ingredients.size)
            binding.instructionsCount.text =
                context.getString(R.string.recipe_card_instructions, recipe.instructions.size)
            binding.deleteButton.setOnClickListener { doOnDeleteClick.invoke(recipe) }
            itemView.setOnClickListener { doOnClick.invoke(recipe) }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
