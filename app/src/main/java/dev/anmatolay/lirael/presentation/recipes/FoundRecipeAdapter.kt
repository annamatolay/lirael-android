package dev.anmatolay.lirael.presentation.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutItemFoundRecipesBinding
import dev.anmatolay.lirael.domain.model.Recipe

class FoundRecipeAdapter(private val dataSet: List<Recipe>) : RecyclerView.Adapter<FoundRecipeAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemFoundRecipesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemFoundRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            binding.servings.text = recipe.servings
            holder.itemView.setOnClickListener {
                // TODO: start cooking flow
                Toast.makeText(holder.itemView.context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
