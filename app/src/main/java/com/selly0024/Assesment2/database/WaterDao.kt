package com.selly0024.Assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.selly0024.Assesment2.model.Water
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Insert suspend fun insert(water: Water)
    @Update suspend fun update(water: Water)

    @Query("SELECT * FROM water WHERE isDeleted = 0 ORDER BY id ASC")
    fun getWater(): Flow<List<Water>>

    @Query("SELECT * FROM water WHERE isDeleted = 1 ORDER BY id ASC")
    fun getDeletedWaters(): Flow<List<Water>>

    @Query("SELECT * FROM water WHERE id = :id")
    suspend fun getWaterById(id: Long): Water?

    @Query("DELETE FROM water WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE water SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Long)

    @Query("UPDATE water SET isDeleted = 0 WHERE id = :id")
    suspend fun restore(id: Long)
}