package dev.anmatolay.lirael.domain.model

data class User(
    val id: String,
    val recipeStatistic: RecipeStatistic = RecipeStatistic(),
) {
    data class RecipeStatistic(
        val cooked: Int = 0,
        val saved: Int = 0,
        val created: Int = 0,
    )
}
