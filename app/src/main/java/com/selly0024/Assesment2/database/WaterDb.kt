package com.selly0024.Assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.selly0024.Assesment2.model.Water

@Database(entities = [Water::class], version = 3, exportSchema = false)
abstract class WaterDb : RoomDatabase() {

    abstract val dao: WaterDao

    companion object {

        @Volatile
        private var INSTANCE: WaterDb? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE water ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE water ADD COLUMN sumberAir TEXT NOT NULL DEFAULT ''"
                )
            }
        }

        fun getInstance(context: Context): WaterDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterDb::class.java,
                    "water.db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    //.fallbackToDestructiveMigration() // Gunakan ini hanya untuk debug
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

