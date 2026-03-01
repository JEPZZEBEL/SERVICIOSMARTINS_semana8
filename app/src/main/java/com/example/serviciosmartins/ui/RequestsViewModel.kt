package com.example.serviciosmartins.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciosmartins.data.RequestEntity
import com.example.serviciosmartins.repo.RequestRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RequestsViewModel(
    private val repo: RequestRepository
) : ViewModel() {

    val requests: StateFlow<List<RequestEntity>> =
        repo.requests.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun create(
        clientName: String,
        serviceType: String,
        address: String,
        phone: String,
        notes: String
    ) {
        viewModelScope.launch {
            repo.insert(
                RequestEntity(
                    clientName = clientName.trim(),
                    serviceType = serviceType.trim(),
                    address = address.trim(),
                    phone = phone.trim(),
                    notes = notes.trim()
                )
            )
        }
    }

    fun update(entity: RequestEntity) {
        viewModelScope.launch { repo.update(entity) }
    }

    fun delete(entity: RequestEntity) {
        viewModelScope.launch { repo.delete(entity) }
    }
}
