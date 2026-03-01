package com.example.serviciosmartins.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    vm: ServicesViewModel,
    onOpenDetail: (Long) -> Unit
) {
    val services by vm.services.collectAsStateWithLifecycle()
    val uiMessage by vm.uiMessage.observeAsState("")
    val quote by vm.quote.observeAsState("Cargando frase...")

    // ✅ Cargar frase al entrar
    LaunchedEffect(Unit) { vm.loadQuote() }

    var title by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    var query by rememberSaveable { mutableStateOf("") }

    val headerImageUrl = remember { "https://picsum.photos/800/300" }

    val filtered = remember(services, query) {
        val q = query.trim().lowercase()
        if (q.isBlank()) services
        else services.filter {
            it.title.lowercase().contains(q) ||
                    it.category.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.phone.lowercase().contains(q)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Servicios") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = headerImageUrl,
                contentDescription = "Header",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .testTag("img_header")
            )

            Spacer(Modifier.height(10.dp))

            // ✅ Evidencia Retrofit (texto que viene de internet)
            Text(
                text = quote,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("txt_quote")
            )

            Spacer(Modifier.height(12.dp))

            // ✅ Buscador
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth().testTag("input_search")
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth().testTag("input_title")
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth().testTag("input_category")
            )

            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().testTag("input_desc")
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth().testTag("input_phone")
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    vm.addService(title, category, desc, phone)
                    title = ""
                    category = ""
                    desc = ""
                    phone = ""
                },
                modifier = Modifier.fillMaxWidth().testTag("btn_add")
            ) {
                Text("Agregar servicio")
            }

            // ✅ Botón para demostrar error controlado (pauta)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { vm.simulateErrorInsert() },
                modifier = Modifier.fillMaxWidth().testTag("btn_simulate_error")
            ) {
                Text("Simular error (debug)")
            }

            if (uiMessage.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = uiMessage,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.testTag("txt_message")
                )
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.testTag("list_services")) {
                items(filtered) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onOpenDetail(service.id) }
                            .testTag("item_${service.id}")
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(service.title, style = MaterialTheme.typography.titleMedium)
                            Text(service.category, style = MaterialTheme.typography.bodyMedium)
                            Text(service.phone, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}