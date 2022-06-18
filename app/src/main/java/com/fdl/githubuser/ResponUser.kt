package com.fdl.githubuser

import com.google.gson.annotations.SerializedName

data class ResponUser(
    @SerializedName("items")
    val data : ArrayList<User>
)


