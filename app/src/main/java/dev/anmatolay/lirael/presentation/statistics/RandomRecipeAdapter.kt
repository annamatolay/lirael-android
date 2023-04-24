package dev.anmatolay.lirael.presentation.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutItemRandomRecipeBinding
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.cooking.CookingSummaryFragment

class RandomRecipeAdapter(private val dataSet: List<Recipe>, private val function: (Recipe) -> Unit) :
    RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemRandomRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemRandomRecipeBinding.inflate(LayoutInflater.from(parent.context))
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
            holder.itemView.setOnClickListener { function.invoke(recipe) }
        }

        val image = recipe.imageUrl ?: R.drawable.cat_error_not_found
        holder.binding.image.load(image) {
            transformations(RoundedCornersTransformation(16f))
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
