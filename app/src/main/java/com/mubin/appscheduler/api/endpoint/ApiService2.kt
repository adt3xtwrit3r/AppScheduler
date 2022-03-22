package com.mubin.appscheduler.api.endpoint

import com.mubin.appscheduler.api.models.ErrorResponse
import com.mubin.appscheduler.api.models.tv_show_epi.TvShowEpi
import com.haroldadmin.cnradapter.NetworkResponse
import com.mubin.appscheduler.helper.Constants
import retrofit2.http.GET

interface ApiService2 {
    @GET(Constants.END_POINT2)
    suspend fun getTvShowsEpi(): NetworkResponse<TvShowEpi, ErrorResponse>
}