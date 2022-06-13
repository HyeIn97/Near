package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
    val id : Int,
    val email : String,
    val phone : String,
    @SerializedName("nick_name")
    val nickName : String,
    @SerializedName("profile_img")
    val profileImg : String
    ) : Serializable