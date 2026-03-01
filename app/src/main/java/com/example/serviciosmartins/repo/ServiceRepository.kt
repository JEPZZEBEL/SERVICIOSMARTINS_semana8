package com.example.serviciosmartins.repo

import com.example.serviciosmartins.data.ServiceDao
import com.example.serviciosmartins.data.ServiceEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class ServiceRepository(
    private val dao: ServiceDao
) {
    fun observeAll(): Flow<List<ServiceEntity>> = dao.observeAll()

    suspend fun insert(service: ServiceEntity) {
        // ✅ Simulación de error (para evidencia del profe)
        if (service.title.contains("ERROR", ignoreCase = true)) {
            Timber.w("Simulación de error activada en insert()")
            throw IllegalStateException("Simulación: fallo controlado al insertar (título contiene 'ERROR')")
        }

        runCatching {
            dao.insert(service)
            Timber.i("DB insert OK: %s", service.title)
        }.onFailure { e ->
            Timber.e(e, "DB insert FAIL")
            throw e
        }
    }

    suspend fun update(service: ServiceEntity) {
        runCatching {
            dao.update(service)
            Timber.i("DB update OK id=%s", service.id)
        }.onFailure { e ->
            Timber.e(e, "DB update FAIL id=%s", service.id)
            throw e
        }
    }

    suspend fun delete(service: ServiceEntity) {
        runCatching {
            dao.delete(service)
            Timber.i("DB delete OK id=%s", service.id)
        }.onFailure { e ->
            Timber.e(e, "DB delete FAIL id=%s", service.id)
            throw e
        }
    }
}