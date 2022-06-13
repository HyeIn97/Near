package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreData(
    val id : Int,
    val name : String,
    @SerializedName("logo_url")
    val img : String
) : Serializable