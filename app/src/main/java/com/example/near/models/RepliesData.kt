package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepliesData(
    val content : String,
    @SerializedName("created_at")
    val date : String,
    val user : UserData
) : Serializable