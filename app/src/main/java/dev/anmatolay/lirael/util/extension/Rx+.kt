package dev.anmatolay.lirael.util.extension

import androidx.room.rxjava3.EmptyResultSetException
import dev.anmatolay.lirael.data.dto.RecipeDto
import dev.anmatolay.lirael.domain.model.Recipe
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

fun <T : Any> T?.toMaybe(): Maybe<T> {
    return if (this == null)
        Maybe.empty()
    else
        Maybe.just(this)
}

fun <T : Any> Single<T>.onEmptyResultSetExceptionResumeNext(default: T) =
    this.onErrorResumeNext { throwable ->
        if (throwable is EmptyResultSetException)
            Single.just(default)
        else
            Single.error(throwable)
    }

fun <dto : RecipeDto> Single<List<dto>>.flatMapToModel() =
    this.flatMap { recipeDtoList ->
        val recipeModelList = mutableListOf<Recipe>()
        recipeDtoList.forEach { recipeModelList.add(it.toModel()) }
        Single.just(recipeModelList.toList())
    }
