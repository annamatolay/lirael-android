package dev.anmatolay.lirael.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutItemRandomRecipeBinding
import dev.anmatolay.lirael.domain.model.Recipe

abstract class RecipeAdapter(private val dataSet: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemRandomRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemRandomRecipeBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    @CallSuper
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        val context = holder.itemView.context
        with(holder) {
            binding.title.text = recipe.title
            binding.ingredientsCount.text =
                context.getString(R.string.recipe_card_ingredients, recipe.ingredients.size)
            binding.instructionsCount.text =
                context.getString(R.string.recipe_card_instructions, recipe.instructions.size)
        }
        holder.itemView.setOnClickListener {
            // TODO: start cooking flow
            Toast.makeText(holder.itemView.context, "Coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
