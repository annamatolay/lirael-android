package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.api.RandomRecipeApi
import dev.anmatolay.lirael.domain.model.Recipe
import io.reactivex.rxjava3.core.Single

class RandomRecipeRepository(private val api: RandomRecipeApi) {

    fun getRecipes(): Single<List<Recipe>> =
        api.service.getRecipes()
            .flatMap { recipeDtoList ->
                val recipeModelList = mutableListOf<Recipe>()
                recipeDtoList.forEach { recipeModelList.add(it.toModel()) }
                Single.just(recipeModelList.toList())
            }
}
