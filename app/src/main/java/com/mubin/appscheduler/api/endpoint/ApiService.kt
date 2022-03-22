package com.mubin.appscheduler.api.endpoint

import com.mubin.appscheduler.api.models.ErrorResponse
import com.mubin.appscheduler.api.models.tv_show.TvShowsModelItem
import com.haroldadmin.cnradapter.NetworkResponse
import com.mubin.appscheduler.helper.Constants
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getTvShows(): NetworkResponse<List<TvShowsModelItem>, ErrorResponse>

}