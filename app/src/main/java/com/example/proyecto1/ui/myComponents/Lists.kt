package com.example.proyecto1.ui.myComponents

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Vehiculo

@Composable
fun ListServicios(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
) {
    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    // TODO esto se puede inicializar de otra manera y no a algo random
    var deletingServicio by remember {
        mutableStateOf(Servicio("a", "a", "a"))
    }

    var stateServicios = viewModel.servicios.collectAsState(initial = emptyList())


    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (servicio in stateServicios.value) {
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
                        Column (
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Text(text = servicio.descripcion)
                            Text(text = servicio.fecha)
                            Text(text = servicio.matricula)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = "Ver")
                        }
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
    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    // TODO esto se puede inicializar de otra manera y no a algo random
    var deletingVehiculo by remember {
        mutableStateOf(Vehiculo(
            matricula = "1234abc", marca = "Mercedes", modelo = "A45 AMG", nombreCliente = "Pepe")
        )
    }

    var stateVehiculos = viewModel.vehiculos.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (vehiculo in stateVehiculos.value) {
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
                        Column (
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Text(text = vehiculo.marca)
                            Text(text = vehiculo.modelo)
                            Text(text = vehiculo.matricula)
                            Text(text = vehiculo.nombreCliente)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                contentDescription = "Ver"
                            )
                        }
                        IconButton(onClick = {
                            showDeleteAlertDialog = true
                            deletingVehiculo = vehiculo
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
                    viewModel.deleteVehiculo(deletingVehiculo)
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
fun ListClientes(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
    navController: NavController
) {
    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    // TODO esto se puede inicializar de otra manera y no a algo random
    var deletingCliente by remember {
        mutableStateOf(Cliente("a", 1, email = "1"))
    }

    var listaClientes = viewModel.clientes.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (cliente in listaClientes.value) {
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
                        Column (
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Text(text = cliente.nombre)
                            Text(text = cliente.email)
                            Text(text = cliente.telefono.toString())
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            navController.navigate("viewCliente/${cliente.nombre}")
                        }) {
                            Icon(painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = "Ver")
                        }
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