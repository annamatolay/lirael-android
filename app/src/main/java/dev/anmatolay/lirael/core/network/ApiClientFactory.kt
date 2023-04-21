package dev.anmatolay.lirael.core.network

import com.squareup.moshi.Moshi
import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.network.interceptor.interceptorList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


object ApiClientFactory {

    private val moshi by inject<Moshi>(Moshi::class.java)

    private const val timeOutInSeconds = 60L

    private val logger =
        HttpLoggingInterceptor.Logger { message -> Timber.tag("Retrofit - OkHttp").d(message) }
    private val loggingInterceptor = HttpLoggingInterceptor(logger).setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

    )
    private val okHttpClientBuilder =
        OkHttpClient().newBuilder()
            .connectTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

    fun <T> createApiImplementation(service: Class<T>, url: String = BuildConfig.API_URL): T {
        interceptorList.forEach { okHttpClientBuilder.addInterceptor(it) }

        return Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .baseUrl(url)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(service)
    }
}
