package com.mubin.appscheduler.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.mubin.appscheduler.api.models.app_model.AppTable

@Database(entities = [AppTable::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

}