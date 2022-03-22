package com.mubin.appscheduler.repository

import com.mubin.appscheduler.api.endpoint.ApiService
import com.mubin.appscheduler.api.endpoint.ApiService2
import com.mubin.appscheduler.api.models.app_model.AppTable
import com.mubin.appscheduler.db.AppDao
import javax.inject.Inject

class AppRepository

@Inject
constructor
    (private val apiService: ApiService,
     private val apiService2: ApiService2,
     private val appDao: AppDao
) {

    suspend fun getTvShows() = apiService.getTvShows()

    suspend fun getTvShowEpi() = apiService2.getTvShowsEpi()

    suspend fun getAllScheduleApps(): List<AppTable> = appDao.getAllScheduleApps()

    suspend fun scheduleApp(app: AppTable): Long = appDao.scheduleApp(app)

    suspend fun getAppInfoFromPackageName(pakName:String): AppTable = appDao.getAppInfoFromPackageName(pakName)

    suspend fun updateSchedule(model: AppTable): Int = appDao.updateSchedule(model)

    suspend fun deleteSchedule(appId: Int): Int = appDao.deleteSchedule(appId)

    suspend fun insertAllInstalledApp(appList: List<AppTable>): List<Long> = appDao.insertInstalledApp(appList)


    suspend fun deleteAllAppData() {
        return appDao.deleteAllAppData()
    }
}