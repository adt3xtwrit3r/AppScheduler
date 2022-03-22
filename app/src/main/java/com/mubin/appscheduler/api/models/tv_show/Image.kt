package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("medium")
    var medium: String = "",
    @SerializedName("original")
    var original: String = ""
)