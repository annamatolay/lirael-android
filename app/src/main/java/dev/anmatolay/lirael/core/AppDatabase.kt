package dev.anmatolay.lirael.core

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.anmatolay.lirael.data.dao.RecipeDao
import dev.anmatolay.lirael.data.dao.UserDao
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.domain.model.User

@Database(
    version = 3,
    entities = [User::class, User.RecipeStatistic::class, Recipe::class],
    exportSchema = true,
)
@TypeConverters(value = [AppDatabase.Converter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun recipeDao(): RecipeDao

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
                    "CREATE TABLE recipe (" +
                            "`id` INT NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`ingredients` TEXT NOT NULL, " +
                            "`instructions` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`)) "
                )
            }
        }
    }

    class Converter {

        @TypeConverter
        fun List<String>.toStringData() = this.toString().drop(1).dropLast(1)

        @TypeConverter
        fun String.toList() = this.split(", ")
    }
}
