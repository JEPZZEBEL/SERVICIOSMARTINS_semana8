package com.example.serviciosmartins

import com.example.serviciosmartins.data.ServiceDao
import com.example.serviciosmartins.data.ServiceEntity
import com.example.serviciosmartins.repo.ServiceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * ✅ Prueba unitaria 2 (MockK + JUnit):
 * Verifica que el repository llame al DAO y retorne el id.
 * Además, simula excepción para cubrir manejo de error.
 */
class ServiceRepositoryTest {

    @Test
    fun `insert delega en dao y retorna id`() {
        val dao = mockk<ServiceDao>()
        val repo = ServiceRepository(dao)
        val entity = ServiceEntity(title="Test", category="Cat", description="Desc ok", phone="123456")

        coEvery { dao.insert(any()) } returns 10L

        val id = kotlinx.coroutines.runBlocking { repo.insert(entity) }

        assertEquals(10L, id)
        coVerify(exactly = 1) { dao.insert(any()) }
    }

    @Test(expected = RuntimeException::class)
    fun `insert propaga excepcion del dao`() {
        val dao = mockk<ServiceDao>()
        val repo = ServiceRepository(dao)
        val entity = ServiceEntity(title="Test", category="Cat", description="Desc ok", phone="123456")

        coEvery { dao.insert(any()) } throws RuntimeException("DB down")

        kotlinx.coroutines.runBlocking { repo.insert(entity) }
    }
}
