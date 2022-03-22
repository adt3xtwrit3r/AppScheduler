package com.mubin.appscheduler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.mubin.appscheduler.api.models.app_model.AppTable
import com.mubin.appscheduler.api.models.tv_show.TvShowsModelItem
import com.mubin.appscheduler.api.models.tv_show_epi.TvShowEpi
import com.mubin.appscheduler.helper.Constants.exhaustive
import com.mubin.appscheduler.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val repository: AppRepository) : ViewModel() {

    fun getTvShows(): LiveData<List<TvShowsModelItem>> {

        val responseBody = MutableLiveData<List<TvShowsModelItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvShows()
            withContext(Dispatchers.Main) {
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                    }
                }.exhaustive
            }
        }
        return responseBody
    }

    fun getTvShowsEpi(): LiveData<TvShowEpi> {

        val responseBody = MutableLiveData<TvShowEpi>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvShowEpi()
            withContext(Dispatchers.Main) {
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                    }
                }.exhaustive
            }
        }
        return responseBody
    }

    fun getAllScheduledApps(): LiveData<List<AppTable>> {
        val responseBody = MutableLiveData<List<AppTable>>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllScheduleApps()
            withContext(Dispatchers.Main) {
                responseBody.value = response
            }
        }
        return responseBody
    }

    fun scheduleApp(app: AppTable): LiveData<Long> {
        val responseBody = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.scheduleApp(app)
            withContext(Dispatchers.Main) {
                responseBody.value = response
            }
        }
        return responseBody
    }

    fun getAppInfoFromPackageName(packageName:String) : LiveData<AppTable> {
        val responseBody = MutableLiveData<AppTable>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAppInfoFromPackageName(packageName)
            withContext(Dispatchers.Main) {
                responseBody.value = response
            }
        }
        return responseBody
    }

    fun updateSchedule(appInfo: AppTable): LiveData<Int> {
        val responseBody = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateSchedule(appInfo)
            withContext(Dispatchers.Main) {
                responseBody.value = response
            }
        }
        return responseBody
    }

    fun deleteSchedule(appId: Int): LiveData<Int> {
        val responseBody = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteSchedule(appId)
            withContext(Dispatchers.Main) {
                responseBody.value = response
            }
        }
        return responseBody
    }

    fun deleteAllAppData(): LiveData<Boolean> {
        val responseBody = MutableLiveData<Boolean>(false)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteAllAppData()
            withContext(Dispatchers.Main) {
                responseBody.value = true
            }
        }
        return responseBody
    }

}