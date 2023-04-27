package dev.anmatolay.lirael.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.anmatolay.lirael.presentation.cooking.step.RecipeAdapterItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import okhttp3.HttpUrl

@Parcelize
@Entity
data class Recipe(
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    val id: Int = 0,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    @IgnoredOnParcel
    val imageUrl: HttpUrl? = null,
    @IgnoredOnParcel
    val servings: String? = null,
) : Parcelable {
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

    fun toRecipeItem(position: Int): RecipeAdapterItem =
        RecipeAdapterItem(
            position,
            instructions.lastIndex,
            this.title,
            this.instructions[position],
            position == instructions.lastIndex,
        )
}
