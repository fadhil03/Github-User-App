package com.fdl.githubuser

import androidx.recyclerview.widget.DiffUtil
import com.fdl.githubuser.database.FavoriteUser

class FavoriteUserDiffCallback(private val mOldFavoriteList: List<FavoriteUser>, private val mNewFavoriteList: List<FavoriteUser>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newEmployee = mNewFavoriteList[newItemPosition]
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        return newEmployee.login == oldEmployee.login
    }

}