package com.fdl.githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: Int,
    @SerializedName("login")
    val username : String,
    val name: String,
    @SerializedName("avatar_url")
    val photo: String,
    @SerializedName("public_repos")
    val repository: String,
    val company: String,
    val location: String,
    val followers: String,
    val following: String,
) : Parcelable

