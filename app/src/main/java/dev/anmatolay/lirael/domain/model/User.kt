package dev.anmatolay.lirael.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    @Embedded
    val recipeStatistic: RecipeStatistic = RecipeStatistic(),
) {

    @Entity
    data class RecipeStatistic(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "recipe_stat_id")
        val id: Int = 0,
        val cooked: Int = 0,
        val saved: Int = 0,
        val created: Int = 0,
    )
}
