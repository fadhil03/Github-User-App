package com.fdl.githubuser

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteViewModelFactory private constructor(private val application: Application): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null

        @JvmStatic
        fun newInstance(application: Application): FavoriteViewModelFactory {
            if (instance == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    instance = FavoriteViewModelFactory(application)
                }
            }
            return instance as FavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class " + modelClass.name)
    }
}