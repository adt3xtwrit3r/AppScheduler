package com.mubin.appscheduler.db

import androidx.room.*
import com.mubin.appscheduler.api.models.app_model.AppTable

@Dao
interface AppDao {

    @Query("SELECT * FROM appTable")
    suspend fun getAllScheduleApps(): List<AppTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledApp(appList: List<AppTable>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun scheduleApp(model: AppTable): Long

    @Query("SELECT * FROM appTable WHERE packageName = :pakName")
    suspend fun getAppInfoFromPackageName(pakName:String): AppTable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSchedule(model: AppTable): Int

    @Query("DELETE FROM appTable WHERE id = :appId" )
    suspend fun deleteSchedule(appId: Int): Int

    @Query("UPDATE appTable SET appSchedule = :newTime WHERE id = :theId")
    suspend fun updateNewScheduledTime(newTime: Long, theId: Int)

    @Query("DELETE FROM appTable")
    suspend fun deleteAllAppData()

}