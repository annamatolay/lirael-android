package dev.anmatolay.lirael.core

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import dev.anmatolay.lirael.BuildConfig
import timber.log.Timber

class SharedPrefHandler(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String? = null): String? =
        sharedPref.getString(key, defaultValue).logResult(key)

    fun putString(key: String, value: String) =
        edit { it.putString(key, value) }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPref.getBoolean(key, defaultValue).logResult(key)

    fun putBoolean(key: String, value: Boolean) =
        edit { it.putBoolean(key, value) }

    fun getInt(key: String, defaultValue: Int = -1) =
        sharedPref.getInt(key, defaultValue).logResult(key)

    fun putInt(key: String, value: Int) =
        edit { it.putInt(key, value) }

    private inline fun <reified T>T.logResult(key: String): T =
        this.apply { Timber.i("${T::class.java.typeName} data retrieved. KEY: $key VALUE: $this") }

    private fun edit(action: (Editor) -> Editor) =
        with(sharedPref.edit()) {
            Timber.tag("SharedPref Editor").i("Data saved.")
            action.invoke(this)
            apply()
        }
}
