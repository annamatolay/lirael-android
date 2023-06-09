package dev.anmatolay.lirael.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.anmatolay.lirael.data.dao.PresetRecipeDao
import dev.anmatolay.lirael.data.dao.RecipeDao
import dev.anmatolay.lirael.data.dao.UserDao
import dev.anmatolay.lirael.domain.model.PresetRecipe
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.Constants.STRING_SEPARATOR

@Database(
    version = 3,
    entities = [User::class, User.RecipeStatistic::class, Recipe::class, PresetRecipe::class],
    exportSchema = true,
)
@TypeConverters(value = [AppDatabase.Converter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun recipeDao(): RecipeDao

    abstract fun presetRecipeDao(): PresetRecipeDao

    class Converter {

        @TypeConverter
        fun List<String>.toStringData() = this.joinToString(STRING_SEPARATOR)

        @TypeConverter
        fun String.toList() = this.split(STRING_SEPARATOR)
    }

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE RecipeStatistic RENAME TO recipe_stat")
                database.execSQL("ALTER TABLE user RENAME COLUMN created TO opened")
                database.execSQL("ALTER TABLE recipe_stat RENAME COLUMN created TO opened")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE recipe_favourite (" +
                            "`id` INT NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`ingredients` TEXT NOT NULL, " +
                            "`instructions` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`)) "
                )

                database.execSQL(
                    "CREATE TABLE recipe_preset (" +
                            "`id` INT NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`ingredients` TEXT NOT NULL, " +
                            "`instructions` TEXT NOT NULL, " +
                            "`servings` TEXT NOT NULL, " +
                            "`category` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`)) "
                )
            }
        }
    }
}
