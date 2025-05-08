package com.selly0024.Assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water")
data class Water(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jumlah: Int,
    val waktu: String,
    val wadah: String
)