package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.dao.PresetRecipeDao
import dev.anmatolay.lirael.domain.model.PresetRecipe
import io.reactivex.rxjava3.core.Single

class PresetRecipeRepository(private val presetRecipeDao: PresetRecipeDao) {

    fun saveAll(presets: List<PresetRecipe>) = presetRecipeDao.createAll(presets)

    fun getAll(category: PresetRecipe.Category) = presetRecipeDao.readAllBy(category)

    fun isEmpty(): Single<Boolean> =
        presetRecipeDao.countId()
            .map { it < 1 }
}
