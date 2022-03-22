package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    var average: Double = 0.0
)