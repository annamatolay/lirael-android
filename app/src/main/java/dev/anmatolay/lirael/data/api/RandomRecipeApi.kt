package dev.anmatolay.lirael.data.api

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
            "X-RapidAPI-Key: e9af0e611fmshdf49bdd5f3fe5c7p139ad7jsn160fee931667",
            "X-RapidAPI-Host: random-recipes.p.rapidapi.com"
        )
        fun getRecipes(): Single<List<RandomRecipeDto>>
    }
}
