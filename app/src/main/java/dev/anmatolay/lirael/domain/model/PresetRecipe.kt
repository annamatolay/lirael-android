package dev.anmatolay.lirael.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_preset")
data class PresetRecipe(
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val servings: String,
    val category: Category,
) {
    @PrimaryKey
    var id: Int = 0

    init {
        this.id = this.hashCode()
    }

    enum class Category {
        EUROPEAN,
        ITALIAN,
        FRENCH,
        AMERICAN,
        ASIAN,
        TURKISH,
        CAKES,
        COCKTAILS,
        LIQUORS,
    }

    fun toRecipe() = Recipe(title, ingredients, instructions, servings)
}
