package com.fdl.githubuser.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fdl.githubuser.database.FavoriteDao
import com.fdl.githubuser.database.FavoriteRoomDatabase



import com.fdl.githubuser.database.FavoriteUser
import java.util.concurrent.ExecutorService

class FavoriteRepository(application: Application) {
    private val favoriteUserDao: FavoriteDao?
    private val executorService: ExecutorService = java.util.concurrent.Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        favoriteUserDao = db?.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = favoriteUserDao!!.getAllFavoriteUser()
/*
    fun delete(id: Int) {
        executorService.execute { favoriteUserDao?.deleteUser(id) }
    }

    fun insert(favoriteModel: FavoriteUser) {
        executorService.execute { favoriteUserDao?.addFavoriteUser(favoriteModel) }
    }

 */

    fun getUser(username: String): String? = favoriteUserDao?.getUser(username)!!

}