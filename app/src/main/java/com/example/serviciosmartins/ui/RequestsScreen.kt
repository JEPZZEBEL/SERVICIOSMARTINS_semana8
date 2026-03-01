package com.example.serviciosmartins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.serviciosmartins.data.RequestEntity
import com.example.serviciosmartins.ui.theme.BgBlack
import com.example.serviciosmartins.ui.theme.CardBlack
import com.example.serviciosmartins.ui.theme.SkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsScreen(vm: RequestsViewModel) {

    val items by vm.requests.collectAsState()

    var sheetOpen by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<RequestEntity?>(null) }

    var clientName by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    fun openCreate() {
        editing = null
        clientName = ""; serviceType = ""; address = ""; phone = ""; notes = ""
        sheetOpen = true
    }

    fun openEdit(r: RequestEntity) {
        editing = r
        clientName = r.clientName
        serviceType = r.serviceType
        address = r.address
        phone = r.phone
        notes = r.notes
        sheetOpen = true
    }

    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { sheetOpen = false },
            containerColor = BgBlack
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 520.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = if (editing == null) "Nueva solicitud" else "Editar solicitud",
                        style = MaterialTheme.typography.titleLarge,
                        color = SkyBlue
                    )

                    OutlinedTextField(
                        value = clientName, onValueChange = { clientName = it },
                        label = { Text("Nombre cliente") }, modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = serviceType, onValueChange = { serviceType = it },
                        label = { Text("Tipo servicio (Gasfitería, Electricidad...)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address, onValueChange = { address = it },
                        label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone, onValueChange = { phone = it },
                        label = { Text("Teléfono") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = notes, onValueChange = { notes = it },
                        label = { Text("Notas") }, modifier = Modifier.fillMaxWidth(),
                        minLines = 2, maxLines = 4
                    )

                    Button(
                        onClick = {
                            val e = editing
                            if (e == null) {
                                vm.create(clientName, serviceType, address, phone, notes)
                            } else {
                                vm.update(
                                    e.copy(
                                        clientName = clientName.trim(),
                                        serviceType = serviceType.trim(),
                                        address = address.trim(),
                                        phone = phone.trim(),
                                        notes = notes.trim()
                                    )
                                )
                            }
                            sheetOpen = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (editing == null) "Guardar" else "Actualizar")
                    }

                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Solicitudes", color = SkyBlue) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BgBlack,
                    titleContentColor = SkyBlue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openCreate() },
                containerColor = SkyBlue,
                contentColor = BgBlack
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva solicitud")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BgBlack)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(items, key = { it.id }) { r ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBlack),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(r.clientName, color = SkyBlue, style = MaterialTheme.typography.titleMedium)
                        Text("Servicio: ${r.serviceType}", color = MaterialTheme.colorScheme.onSurface)
                        Text("Dirección: ${r.address}", color = MaterialTheme.colorScheme.onSurface)
                        Text("Tel: ${r.phone}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                        Text(r.notes, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(
                                onClick = { openEdit(r) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text("Editar")
                            }
                            OutlinedButton(
                                onClick = { vm.delete(r) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
