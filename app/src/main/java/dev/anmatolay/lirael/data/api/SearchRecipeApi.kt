package dev.anmatolay.lirael.data.api

import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.network.ApiClientFactory
import dev.anmatolay.lirael.data.dto.SearchRecipeDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


class SearchRecipeApi(apiClientFactory: ApiClientFactory, url: String) {

    var service = apiClientFactory.createApiImplementation(SearchRecipeService::class.java, url)

    interface SearchRecipeService {

        @GET("v1/recipe")
        @Headers(
            "X-Api-Key: ${BuildConfig.API_NINJAS_KEY}"
        )
        fun getRecipes(@Query("query") keyword: String): Single<List<SearchRecipeDto>>
    }
}
