package dev.anmatolay.lirael.data.dao

import androidx.room.*
import dev.anmatolay.lirael.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: User): Completable

    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun read(id: String): Single<User>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: User): Completable

    // If user turns off anonymous data sharing there will be two user entry
    @Query("DELETE FROM user")
    fun deleteAll(): Completable
}
