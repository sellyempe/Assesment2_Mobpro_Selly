package com.selly0024.Assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.selly0024.Assesment2.model.Water
import com.selly0024.Assesment2.database.WaterDao

@Database(entities = [Water::class], version = 1, exportSchema = false)
abstract class WaterDb : RoomDatabase() {

    abstract val dao: WaterDao

    companion object {
        @Volatile
        private var INSTANCE: WaterDb? = null

        fun getInstance(context: Context): WaterDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WaterDb::class.java,
                        "water_tracker.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
