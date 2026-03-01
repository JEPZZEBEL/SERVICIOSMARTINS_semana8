package com.example.serviciosmartins

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class ServicesFlowUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addService_and_openDetail() {
        val title = "Test Servicio UI"
        val category = "Categoria UI"
        val desc = "Descripcion UI"
        val phone = "12345678"

        // Esperar a que la pantalla principal exista
        composeRule.onNodeWithTag("input_title").assertIsDisplayed()

        // Completar formulario
        composeRule.onNodeWithTag("input_title").performTextInput(title)
        composeRule.onNodeWithTag("input_category").performTextInput(category)
        composeRule.onNodeWithTag("input_desc").performTextInput(desc)
        composeRule.onNodeWithTag("input_phone").performTextInput(phone)

        // Click en Agregar (antes el test buscaba fab_add, ahora es btn_add)
        composeRule.onNodeWithTag("btn_add").performClick()

        // Verificar que aparece en la lista por texto (más robusto que depender del id)
        composeRule.onNodeWithText(title).assertIsDisplayed()

        // Abrir detalle
        composeRule.onNodeWithText(title).performClick()

        // Verificar que estamos en pantalla detalle
        composeRule.onNodeWithTag("detail_input_title").assertIsDisplayed()

        // Volver
        composeRule.onNodeWithTag("btn_back").performClick()

        // Verificar que volvimos a la lista
        composeRule.onNodeWithTag("list_services").assertIsDisplayed()
    }
}