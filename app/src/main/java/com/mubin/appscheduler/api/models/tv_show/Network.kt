package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName
import com.mubin.appscheduler.api.models.tv_show.Country

data class Network(
    @SerializedName("country")
    var country: Country = Country(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
)