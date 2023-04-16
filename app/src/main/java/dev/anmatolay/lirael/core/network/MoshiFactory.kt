package dev.anmatolay.lirael.core.network

import com.squareup.moshi.Moshi

object MoshiFactory {

    fun create(): Moshi {
        val builder = Moshi.Builder()
        jsonAdapterList.forEach { builder.add(it) }

        return builder.build()
    }
}
