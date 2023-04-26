package dev.anmatolay.lirael.core

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.anmatolay.lirael.data.dao.UserDao
import dev.anmatolay.lirael.domain.model.User

@Database(
    version = 2,
    entities = [User::class, User.RecipeStatistic::class],
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE RecipeStatistic RENAME TO recipe_stat")
                database.execSQL("ALTER TABLE user RENAME COLUMN created TO opened")
                database.execSQL("ALTER TABLE recipe_stat RENAME COLUMN created TO opened")
            }
        }
    }
}
