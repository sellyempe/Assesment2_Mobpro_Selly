package com.selly0024.Assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selly0024.Assesment2.database.WaterDao
import com.selly0024.Assesment2.model.Water
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: WaterDao) : ViewModel() {
    val data: StateFlow<List<Water>> = dao.getAllWater().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
