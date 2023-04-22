package dev.anmatolay.lirael.data.dto

import dev.anmatolay.lirael.domain.model.Recipe
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

abstract class RecipeDto {
    abstract fun toModel(): Recipe
}
