package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaymentData(
    val id : Int,
    @SerializedName("created_at")
    val date : String,
    val subscription : SubscriptionData
) : Serializable