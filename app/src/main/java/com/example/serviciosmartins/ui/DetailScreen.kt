package com.example.serviciosmartins.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Long,
    vm: ServicesViewModel,
    onBack: () -> Unit
) {
    val services by vm.services.collectAsState()
    val service = services.firstOrNull { it.id == id }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        if (service == null) {
            Text(
                "Servicio no encontrado",
                modifier = Modifier.padding(padding).padding(16.dp)
            )
            return@Scaffold
        }

        var title by rememberSaveable { mutableStateOf(service.title) }
        var category by rememberSaveable { mutableStateOf(service.category) }
        var desc by rememberSaveable { mutableStateOf(service.description) }
        var phone by rememberSaveable { mutableStateOf(service.phone) }

        Column(
            modifier = Modifier.padding(padding).padding(16.dp)
        ) {
            Text(service.title, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth().testTag("detail_input_title")
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth().testTag("detail_input_category")
            )

            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().testTag("detail_input_desc")
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth().testTag("detail_input_phone")
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    vm.updateService(
                        service.copy(
                            title = title.trim(),
                            category = category.trim(),
                            description = desc.trim(),
                            phone = phone.trim()
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().testTag("detail_btn_update")
            ) {
                Text("Actualizar")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    vm.deleteService(service)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth().testTag("detail_btn_delete")
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(Modifier.height(6.dp))
                Text("Eliminar")
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "Debug: revisa Logcat (Timber) y LeakCanary en debug.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}