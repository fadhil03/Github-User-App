package com.fdl.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteRoomDatabase: RoomDatabase() {
        abstract fun favoriteDao(): FavoriteDao
        companion object {
           @Volatile
           var INSTANCE: FavoriteRoomDatabase? = null
           @JvmStatic
          fun getDatabase(context: Context): FavoriteRoomDatabase? {
              if (INSTANCE == null) {
                  synchronized(FavoriteRoomDatabase::class) {
                      INSTANCE = Room.databaseBuilder(context.applicationContext,
                          FavoriteRoomDatabase::class.java, "favorite_database")
                          .build()
                  }
              }
              return INSTANCE as FavoriteRoomDatabase
          }
        }
}
