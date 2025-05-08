package com.selly0024.Assesment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.selly0024.Assesment2.database.WaterDao
import com.selly0024.Assesment2.ui.screen.DetailViewModel
import com.selly0024.Assesment2.ui.screen.MainViewModel


class ViewModelFactory(
    private val dao: WaterDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

