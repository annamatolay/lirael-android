package dev.anmatolay.lirael.core

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.anmatolay.lirael.data.dao.UserDao
import dev.anmatolay.lirael.domain.model.User

@Database(entities = [User::class, User.RecipeStatistic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
