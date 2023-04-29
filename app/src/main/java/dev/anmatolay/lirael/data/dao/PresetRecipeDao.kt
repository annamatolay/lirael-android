package dev.anmatolay.lirael.data.dao

import androidx.room.*
import dev.anmatolay.lirael.domain.model.PresetRecipe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PresetRecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createAll(presets: List<PresetRecipe>): Completable

    @Query("SELECT * FROM recipe_preset WHERE category LIKE :category")
    fun readAllBy(category: PresetRecipe.Category): Single<List<PresetRecipe>>

    @Query("SELECT COUNT(id) FROM recipe_preset")
    fun countId(): Single<Int>
}
