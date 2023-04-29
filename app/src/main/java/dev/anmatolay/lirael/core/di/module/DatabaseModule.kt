package dev.anmatolay.lirael.core.di.module

import androidx.room.Room
import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.AppDatabase
import dev.anmatolay.lirael.core.AppDatabase.Companion.MIGRATION_1_2
import dev.anmatolay.lirael.core.AppDatabase.Companion.MIGRATION_2_3
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// TODO: create test db for UI tests
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.APPLICATION_ID + ".db",
        ).addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
        ).fallbackToDestructiveMigrationOnDowngrade().build()
    }

    single { get<AppDatabase>().userDao() }

    single { get<AppDatabase>().recipeDao() }
}
