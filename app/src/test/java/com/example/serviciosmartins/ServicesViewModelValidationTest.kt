package com.example.serviciosmartins

import com.example.serviciosmartins.ui.ServicesViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * ✅ Prueba unitaria 1 (JUnit):
 * Validación de datos antes de insertar (lógica de negocio).
 * Esto cumple con "al menos dos clases de prueba" fileciteturn1file0L45-L51
 */
class ServicesViewModelValidationTest {

    @Test
    fun `titulo corto falla`() {
        val r = ServicesViewModel.validateServiceInput(
            title = "Hi",
            category = "Aseo",
            desc = "Desc ok",
            phone = "123456"
        )
        assertFalse(r.ok)
    }

    @Test
    fun `datos correctos pasan`() {
        val r = ServicesViewModel.validateServiceInput(
            title = "Plomería",
            category = "Hogar",
            desc = "Reparación de cañerías",
            phone = "987654"
        )
        assertTrue(r.ok)
    }
}
