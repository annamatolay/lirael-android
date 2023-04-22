package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.api.SearchRecipeApi
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.extension.flatMapToModel
import io.reactivex.rxjava3.core.Single

class SearchRecipeRepository(private val api: SearchRecipeApi) {

    fun getRecipes(keyword: String): Single<List<Recipe>> =
        api.service
            .getRecipes(keyword)
            .flatMapToModel()
}
