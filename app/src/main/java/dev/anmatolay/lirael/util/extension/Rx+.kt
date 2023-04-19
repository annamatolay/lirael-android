package dev.anmatolay.lirael.util.extension

import androidx.room.rxjava3.EmptyResultSetException
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

fun < T : Any> T?.toMaybe(): Maybe<T> {
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
