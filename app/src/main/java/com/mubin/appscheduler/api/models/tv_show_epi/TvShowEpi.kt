package com.mubin.appscheduler.api.models.tv_show_epi


import com.google.gson.annotations.SerializedName
import com.mubin.appscheduler.api.models.tv_show_epi.TvShow

data class TvShowEpi(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("pages")
    var pages: Int = 0,
    @SerializedName("total")
    var total: String = "",
    @SerializedName("tv_shows")
    var tvShows: List<TvShow> = listOf()
)