package com.dicoding.aetherized.aetherizedgitdeck.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.aetherized.aetherizedgitdeck.data.User

class FavoriteDiffCallback(private val mOldFavoriteList: List<User>, private val mNewFavoriteList: List<User>) : DiffUtil.Callback() {
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
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        val newEmployee = mNewFavoriteList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.name == newEmployee.name  && oldEmployee.avatarUrl == newEmployee.avatarUrl && oldEmployee.followers == newEmployee.followers  && oldEmployee.following == newEmployee.following
    }
}