package com.selly0024.Assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selly0024.Assesment2.database.WaterDao
import com.selly0024.Assesment2.model.RecycleBin
import com.selly0024.Assesment2.model.Water
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: WaterDao) : ViewModel() {

    suspend fun getWaterById(id: Long): Water? {
        return dao.getWaterById(id)
    }

    fun insert(jumlah: Int, waktu: String, wadah: String) {
        val entry = Water(
            jumlah = jumlah,
            waktu = waktu,
            wadah = wadah
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(entry)
        }
    }

    fun update(id: Long, jumlah: Int, waktu: String, wadah: String) {
        val entry = Water(
            id = id,
            jumlah = jumlah,
            waktu = waktu,
            wadah = wadah
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entry)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun moveToRecycleBin(id: Long) {
        viewModelScope.launch {
            val water = dao.getWaterById(id)
            if (water != null) {
                // Assuming RecycleBin model has isDeleted flag
                val deleted = RecycleBin(
                    id = 0,  // New ID for recycle bin item
                    jumlah = water.jumlah,
                    waktu = water.waktu,
                    wadah = water.wadah,
                )

                dao.insertToRecycleBin(deleted)
                dao.delete(water)  // Delete the original entry from Water table
            }
        }
    }
}
