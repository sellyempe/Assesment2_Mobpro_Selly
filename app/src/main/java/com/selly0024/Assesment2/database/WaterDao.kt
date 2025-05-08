package com.selly0024.Assesment2.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.selly0024.Assesment2.model.Water
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert
    suspend fun insert(entry: Water)

    @Update
    suspend fun update(entry: Water)

    @Delete
    suspend fun delete(entry: Water)


    @Query("SELECT * FROM water ORDER BY waktu DESC")
    fun getAllWater(): Flow<List<Water>>

    @Query("DELETE FROM water WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM water WHERE id = :id")
    suspend fun getWaterById(id: Long): Water?
}