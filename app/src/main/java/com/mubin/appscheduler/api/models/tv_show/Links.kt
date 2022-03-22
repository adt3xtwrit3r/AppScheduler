package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("previousepisode")
    var previousepisode: Previousepisode = Previousepisode(),
    @SerializedName("self")
    var self: Self = Self()
)