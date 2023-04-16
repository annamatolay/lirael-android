package dev.anmatolay.lirael.core.network.interceptor

import okhttp3.Interceptor

val interceptorList: List<Interceptor> = listOf(
    userAgentInterceptor,
)
