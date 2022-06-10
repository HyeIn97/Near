package com.example.near.models

import com.google.gson.annotations.SerializedName

data class CardData (
    val id : Int,
    @SerializedName("card_num")
    val cardNum : String,
    @SerializedName("card_nickname")
    val cardNick : String,
    @SerializedName("mm_yy")
    val cardPeriod : String
        ) {
}