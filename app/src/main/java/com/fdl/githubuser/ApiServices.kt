package com.fdl.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") username: String
    ) : Call<ResponUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<User>>
}