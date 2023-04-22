package dev.anmatolay.lirael.presentation.statistics

import coil.load
import coil.transform.RoundedCornersTransformation
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.RecipeAdapter

class RandomRecipeAdapter(private val dataSet: List<Recipe>) : RecipeAdapter(dataSet) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val recipe = dataSet[position]
        val image = recipe.imageUrl ?: R.drawable.cat_error_not_found
        holder.binding.image.load(image) {
            transformations(RoundedCornersTransformation(16f))
        }
    }
}
