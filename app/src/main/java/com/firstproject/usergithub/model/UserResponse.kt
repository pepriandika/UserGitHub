package com.firstproject.usergithub.model

import com.google.gson.annotations.SerializedName


data class UserList(

    @field:SerializedName("items")
    val items: List<UserResponse>

)



data class UserResponse(
    @field:SerializedName("following_url")
    val followingUrl: String?,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("followers_url")
    val followersUrl: String?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String?,
)
