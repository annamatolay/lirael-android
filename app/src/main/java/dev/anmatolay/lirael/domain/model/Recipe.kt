package dev.anmatolay.lirael.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import dev.anmatolay.lirael.presentation.cooking.step.RecipeAdapterItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import okhttp3.HttpUrl

@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
) : Parcelable {
    @PrimaryKey
    @IgnoredOnParcel
    var id: Int = 0

    @Ignore
    @IgnoredOnParcel
    var imageUrl: HttpUrl? = null

    @Ignore
    @IgnoredOnParcel
    var servings: String? = null

    constructor(title: String, ingredients: List<String>, instructions: List<String>, imageUrl: HttpUrl?) : this(
        title = title,
        ingredients = ingredients,
        instructions = instructions,
    ) {
        this.imageUrl = imageUrl
    }

    constructor(title: String, ingredients: List<String>, instructions: List<String>, servings: String) : this(
        title = title,
        ingredients = ingredients,
        instructions = instructions,
    ) {
        this.servings = servings
    }

    init {
        this.id = this.hashCode()
    }

    fun toRecipeItem(position: Int): RecipeAdapterItem =
        RecipeAdapterItem(
            position,
            instructions.lastIndex,
            this.title,
            this.instructions[position],
            position == instructions.lastIndex,
        )
}
