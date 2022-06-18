package com.fdl.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    fun addFavoriteUser(favoriteModel: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM FavoriteUser WHERE username = (:username)")
    fun getUser(username: String): String

    @Query("DELETE FROM FavoriteUser WHERE id = (:id)")
    fun deleteUser(id: Int) : Int
}