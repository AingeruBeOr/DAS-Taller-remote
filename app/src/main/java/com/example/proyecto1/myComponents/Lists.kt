package com.example.proyecto1.myComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.Cliente
import com.example.proyecto1.Servicio

@Composable
fun ListServicios(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    // TODO esto se puede inicializar de otra manera y no a algo random
    var deletingServicio: Servicio = Servicio("a", "a", "a")

    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (servicio in viewModel.servicios) {
            item {
                ElevatedCard (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(text = servicio.descripcion)
                            Text(text = servicio.fecha)
                            Text(text = servicio.matricula)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                deletingServicio = servicio
                                showDeleteAlertDialog = true
                            }
                            ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Eliminar",
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDeleteAlertDialog) {
        AlertDialog(
            title = { Text(text = "¿Estás seguro?") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar el elemento seleccionado?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteServicio(deletingServicio)
                    showDeleteAlertDialog = false
                }) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteAlertDialog = false }) {
                    Text(text = "Cancelar")
                }
            },
            onDismissRequest = { showDeleteAlertDialog = false },
        )
    }
}

@Composable
fun ListVehículos(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (vehiculo in viewModel.vehiculos) {
            item {
                ElevatedCard (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(text = vehiculo.marca)
                            Text(text = vehiculo.modelo)
                            Text(text = vehiculo.matricula)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.deleteVehiculo(vehiculo) }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Eliminar",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListClientes(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    var deletingCliente: Cliente = Cliente("a", 1, email = "1")

    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (cliente in viewModel.clientes) {
            item {
                ElevatedCard (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(text = cliente.nombre)
                            Text(text = cliente.email)
                            Text(text = cliente.telefono.toString())
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            deletingCliente = cliente
                            showDeleteAlertDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Eliminar",
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDeleteAlertDialog) {
        AlertDialog(
            title = { Text(text = "¿Estás seguro?") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar el elemento seleccionado?") },
            confirmButton = {
                Button(onClick = {
                    showDeleteAlertDialog = false
                    viewModel.deleteCliente(deletingCliente)
                }) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteAlertDialog = false }) {
                    Text(text = "Cancelar")
                }
            },
            onDismissRequest = { showDeleteAlertDialog = false },
        )
    }
}