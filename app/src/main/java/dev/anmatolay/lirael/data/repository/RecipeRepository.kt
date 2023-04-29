package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.api.RandomRecipeApi
import dev.anmatolay.lirael.data.api.SearchRecipeApi
import dev.anmatolay.lirael.data.dao.RecipeDao
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.util.extension.flatMapToModel
import io.reactivex.rxjava3.core.Single

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val randomRecipeApi: RandomRecipeApi,
    private val searchRecipeApi: SearchRecipeApi

) {
    fun save(recipe: Recipe) = recipeDao.create(recipe)

    fun get(title: String) = recipeDao.read(title)

    fun getAll() = recipeDao.readAll()

    fun delete(recipe: Recipe) = recipeDao.delete(recipe)

    fun getRecipes(): Single<List<Recipe>> =
        randomRecipeApi.service
            .getRecipes()
            .flatMapToModel()

    fun getRecipes(keyword: String): Single<List<Recipe>> =
        searchRecipeApi.service
            .getRecipes(keyword)
            .flatMapToModel()
}
