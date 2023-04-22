package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.api.RandomRecipeApi
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.extension.flatMapToModel
import io.reactivex.rxjava3.core.Single

class RandomRecipeRepository(private val api: RandomRecipeApi) {

    fun getRecipes(): Single<List<Recipe>> =
        api.service
            .getRecipes()
            .flatMapToModel()
}
