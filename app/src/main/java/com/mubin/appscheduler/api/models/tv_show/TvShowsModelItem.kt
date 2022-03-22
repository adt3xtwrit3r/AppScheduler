package com.mubin.appscheduler.api.models.tv_show


import com.google.gson.annotations.SerializedName
import com.mubin.appscheduler.api.models.tv_show.*

data class TvShowsModelItem(
    @SerializedName("averageRuntime")
    var averageRuntime: Int = 0,
    @SerializedName("dvdCountry")
    var dvdCountry: Any = Any(),
    @SerializedName("ended")
    var ended: String = "",
    @SerializedName("externals")
    var externals: Externals = Externals(),
    @SerializedName("genres")
    var genres: List<String> = listOf(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("image")
    var image: Image = Image(),
    @SerializedName("language")
    var language: String = "",
    @SerializedName("_links")
    var links: Links = Links(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("network")
    var network: Network = Network(),
    @SerializedName("officialSite")
    var officialSite: String = "",
    @SerializedName("premiered")
    var premiered: String = "",
    @SerializedName("rating")
    var rating: Rating = Rating(),
    @SerializedName("runtime")
    var runtime: Int = 0,
    @SerializedName("schedule")
    var schedule: Schedule = Schedule(),
    @SerializedName("status")
    var status: String = "",
    @SerializedName("summary")
    var summary: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("updated")
    var updated: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("webChannel")
    var webChannel: Any = Any(),
    @SerializedName("weight")
    var weight: Int = 0
)