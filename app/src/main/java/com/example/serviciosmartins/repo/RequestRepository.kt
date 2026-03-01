package com.example.serviciosmartins.repo

import com.example.serviciosmartins.data.RequestDao
import com.example.serviciosmartins.data.RequestEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class RequestRepository(
    private val dao: RequestDao
) {
    val requests: Flow<List<RequestEntity>> = dao.observeAll()

    suspend fun insert(entity: RequestEntity) =
        runCatching { dao.insert(entity) }
            .onSuccess { Timber.d("Request insert OK id=%s", it) }
            .onFailure { Timber.e(it, "Error insert Request") }
            .getOrThrow()

    suspend fun update(entity: RequestEntity) =
        runCatching { dao.update(entity) }
            .onFailure { Timber.e(it, "Error update Request id=%s", entity.id) }
            .getOrThrow()

    suspend fun delete(entity: RequestEntity) =
        runCatching { dao.delete(entity) }
            .onFailure { Timber.e(it, "Error delete Request id=%s", entity.id) }
            .getOrThrow()

    suspend fun getById(id: Long): RequestEntity? =
        runCatching { dao.getById(id) }
            .onFailure { Timber.e(it, "Error get Request id=%s", id) }
            .getOrNull()
}
