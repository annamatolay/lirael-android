package dev.anmatolay.lirael.data.dao

import androidx.room.*
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(recipe: Recipe): Completable

    @Query("SELECT * FROM recipe_favourite WHERE title LIKE :title")
    fun read(title: String): Single<Recipe>

    @Query("SELECT * FROM recipe_favourite")
    fun readAll(): Single<List<Recipe>>

    @Delete
    fun delete(recipe: Recipe): Completable
}
