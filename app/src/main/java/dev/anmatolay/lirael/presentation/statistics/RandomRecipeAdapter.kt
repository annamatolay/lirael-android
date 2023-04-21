package dev.anmatolay.lirael.presentation.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutItemRandomRecipeBinding
import dev.anmatolay.lirael.domain.model.Recipe

class RandomRecipeAdapter(private val dataSet: List<Recipe>) : RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder>() {

    private lateinit var binding: LayoutItemRandomRecipeBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutItemRandomRecipeBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        val context = holder.itemView.context
        recipe.run {
            binding.title.text = recipe.title
            binding.ingredientsCount.text =
                context.getString(R.string.recipe_card_ingredients, recipe.ingredients.size)
            binding.instructionsCount.text =
                context.getString(R.string.recipe_card_ingredients, recipe.ingredients.size)
            val image = recipe.imageUrl ?: R.drawable.cat_error_not_found
            // FIXME : img not loading
            binding.image.load(image)
        }

    }

    override fun getItemCount(): Int = dataSet.size

}
