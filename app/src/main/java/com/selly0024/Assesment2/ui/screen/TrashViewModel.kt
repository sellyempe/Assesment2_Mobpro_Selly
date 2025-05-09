package com.selly0024.Assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selly0024.Assesment2.database.WaterDao
import com.selly0024.Assesment2.model.Water
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrashViewModel(private val dao: WaterDao) : ViewModel() {

    val data: StateFlow<List<Water>> = dao.getDeletedWaters().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun pulihkan(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restore(id)
        }
    }

    fun hapusPermanen(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}


