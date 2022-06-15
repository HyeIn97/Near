package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PointData(
    @SerializedName("review_id")
    val reviewId : Int,
    val amount : Int
): Serializable