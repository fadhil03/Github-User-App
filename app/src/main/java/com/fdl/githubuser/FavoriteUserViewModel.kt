package com.fdl.githubuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fdl.githubuser.database.FavoriteDao
import com.fdl.githubuser.database.FavoriteRoomDatabase
import com.fdl.githubuser.database.FavoriteUser
import com.fdl.githubuser.database.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val favoriteUserDao: FavoriteDao?

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        favoriteUserDao = db?.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = favoriteRepository.getAllFavorite()

    fun addFavorite(id: Int, username: String, avatar: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val users = FavoriteUser(
                id,
                username,
                avatar
            )
            favoriteUserDao?.addFavoriteUser(users)
        }
    }

    fun deleteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.deleteUser(id)
        }
    }

    fun getUser(username: String): String? = favoriteRepository.getUser(username)

}