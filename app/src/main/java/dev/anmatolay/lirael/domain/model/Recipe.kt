package dev.anmatolay.lirael.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.HttpUrl

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    val id: Int = 0,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrl: HttpUrl? = null,
    val servings: String? = null,
) {
    constructor(title: String, ingredients: List<String>, instructions: List<String>, imageUrl: HttpUrl?) : this(
        id = 0,
        title = title,
        ingredients = ingredients,
        instructions = instructions,
        imageUrl = imageUrl,
    )

    constructor(title: String, ingredients: List<String>, instructions: List<String>, servings: String) : this(
        id = 0,
        title = title,
        ingredients = ingredients,
        instructions = instructions,
        servings = servings,
    )
}
