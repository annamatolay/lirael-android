package dev.anmatolay.lirael.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiFactory {

    fun create(): Moshi {
        val builder = Moshi.Builder()
        jsonAdapterList.forEach { builder.add(it) }
        builder.add(KotlinJsonAdapterFactory())
        return builder.build()
    }
}
