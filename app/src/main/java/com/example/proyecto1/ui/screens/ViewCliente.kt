package com.example.proyecto1.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun ViewCliente(
    navController: NavController,
    viewModel: ActivityViewModel,
    nombreCliente: String?,
    openDial: (Int) -> Unit,
    sendMail: (String) -> Unit
) {
    var cliente by remember {
        mutableStateOf(Cliente("a", 1, "1"))
    }

    if (nombreCliente != null) {
        cliente = viewModel.getUserDataFromName(nombreCliente)
    }

    val vehiculosDelCliente = nombreCliente?.let {
        viewModel.getClientVehicles(it).collectAsState(initial = emptyList()).value
    }

    var deletingVehiculo by remember {
        mutableStateOf(
            Vehiculo(
            matricula = "1234abc", marca = "Mercedes", modelo = "A45 AMG", nombreCliente = "Pepe")
        )
    }

    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    Scaffold (
        topBar = {
            TopBar(
                title = stringResource(id = R.string.Info_client),
                showSettings = false,
                showBackNavArrow = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(all = 15.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 10.dp)
            ) {
                Icon(Icons.Rounded.Person, contentDescription = "Nombre")
                Text(text = cliente.nombre)
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 10.dp)
            ) {
                Icon(Icons.Rounded.Phone, contentDescription = "Teléfono")
                Text(text = cliente.telefono.toString())
                Button(onClick = {
                    openDial(cliente.telefono)
                }) {
                    Text(text = stringResource(id = R.string.Call))
                }
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 10.dp)
            )  {
                Icon(Icons.Rounded.Email, contentDescription = "Email")
                Text(text = cliente.email)
                Button(onClick = {
                    sendMail(cliente.email)
                }) {
                    Text(text = stringResource(id = R.string.Send_mail))
                }
            }
            Text(
                text = "Vehículos del cliente",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn {
                if (vehiculosDelCliente != null) {
                    for (vehiculo in vehiculosDelCliente) {
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
                                    IconButton(onClick = {
                                        navController.navigate("viewVehiculo/${vehiculo.matricula}")
                                    }) {
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
    }
}