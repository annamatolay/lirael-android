package dev.anmatolay.lirael.data.api

import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.network.ApiClientFactory
import dev.anmatolay.lirael.data.dto.RandomRecipeDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


class RandomRecipeApi(apiClientFactory: ApiClientFactory, url: String) {

    var service = apiClientFactory.createApiImplementation(RandomRecipeService::class.java, url)

    interface RandomRecipeService {

        @GET("/ai-quotes/6")
        @Headers(
            "X-RapidAPI-Key: ${BuildConfig.API_RAPID_KEY}",
            "X-RapidAPI-Host: random-recipes.p.rapidapi.com"
        )
        fun getRecipes(): Single<List<RandomRecipeDto>>
    }
}
