package com.example.serviciosmartins.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciosmartins.data.ServiceEntity
import com.example.serviciosmartins.repo.QuoteRepository
import com.example.serviciosmartins.repo.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class ServicesViewModel(
    private val serviceRepo: ServiceRepository
) : ViewModel() {

    // ✅ Retrofit repo
    private val quoteRepo = QuoteRepository()

    // ✅ Room list
    val services: StateFlow<List<ServiceEntity>> =
        serviceRepo.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiMessage = MutableLiveData("")
    val uiMessage: LiveData<String> = _uiMessage

    private val _quote = MutableLiveData("Cargando frase...")
    val quote: LiveData<String> = _quote

    fun loadQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = quoteRepo.fetchRandomQuote() // ✅ Result<String>
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _quote.postValue(content)
                Timber.tag("VM").i("Quote loaded OK")
            } else {
                _quote.postValue("No se pudo cargar la frase (sin internet o API caída).")
                Timber.tag("VM").e(result.exceptionOrNull(), "Quote load FAIL")
            }
        }
    }

    fun addService(title: String, category: String, description: String, phone: String) {
        val t = title.trim()
        val c = category.trim()
        val d = description.trim()
        val p = phone.trim()

        if (t.isBlank()) {
            _uiMessage.postValue("⚠️ El título no puede estar vacío.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                serviceRepo.insert(
                    ServiceEntity(
                        title = t,
                        category = c,
                        description = d,
                        phone = p
                    )
                )
                _uiMessage.postValue("✅ Servicio agregado.")
                Timber.tag("VM").i("addService OK: %s", t)
            } catch (e: Exception) {
                val msg = if (t.contains("ERROR", ignoreCase = true)) {
                    "✅ Error SIMULADO activado (debug). Revisa Logcat."
                } else {
                    "❌ Error al insertar (revisa Logcat)."
                }
                _uiMessage.postValue(msg)
                Timber.tag("VM").e(e, "VM addService FAIL")
            }
        }
    }

    fun updateService(service: ServiceEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                serviceRepo.update(service)
                _uiMessage.postValue("✅ Servicio actualizado.")
                Timber.tag("VM").i("update OK id=%s", service.id)
            } catch (e: Exception) {
                _uiMessage.postValue("❌ Error al actualizar (revisa Logcat).")
                Timber.tag("VM").e(e, "update FAIL id=%s", service.id)
            }
        }
    }

    fun deleteService(service: ServiceEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                serviceRepo.delete(service)
                _uiMessage.postValue("🗑️ Servicio eliminado.")
                Timber.tag("VM").i("delete OK id=%s", service.id)
            } catch (e: Exception) {
                _uiMessage.postValue("❌ Error al eliminar (revisa Logcat).")
                Timber.tag("VM").e(e, "delete FAIL id=%s", service.id)
            }
        }
    }

    fun simulateErrorInsert() {
        addService("ERROR servicio demo", "debug", "Esto debe fallar", "123")
    }
}