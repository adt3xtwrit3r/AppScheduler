package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("imdb")
    var imdb: String = "",
    @SerializedName("thetvdb")
    var thetvdb: Int = 0,
    @SerializedName("tvrage")
    var tvrage: Int = 0
)