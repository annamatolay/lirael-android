package dev.anmatolay.lirael.data.local

import dev.anmatolay.lirael.util.Constants.KEY_USER_ID
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.util.extension.toMaybe
import io.reactivex.rxjava3.core.Maybe

class UserIdDataSource(private val sharedPrefHandler: SharedPrefHandler) {

    fun getUserId(): Maybe<String> =
        sharedPrefHandler
            .getString(KEY_USER_ID)
            .toMaybe()

    fun putUserId(id: String) =
        sharedPrefHandler.putString(KEY_USER_ID, id)
}
