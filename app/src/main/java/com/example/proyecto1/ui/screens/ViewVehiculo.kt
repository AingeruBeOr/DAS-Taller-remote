package com.example.proyecto1.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun ViewVehiculo(
    navController: NavController,
    viewModel: ActivityViewModel,
    matricula: String?
) {
    var vehiculo by remember {
        mutableStateOf(Vehiculo("a", "a", "1", "a"))
    }

    if (matricula != null) {
        vehiculo = viewModel.getVehicleDataFromMatricula(matricula)
    }

    val serviciosDelVehiculo = matricula?.let {
        viewModel.getVehicleServices(it).collectAsState(initial = emptyList()).value
    }

    var deletingServicio by remember {
        mutableStateOf(
            Servicio("a", "a", "a")
        )
    }

    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Scaffold (
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.Info_vehicle),
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
                Row { Text(text = "Nombre cliente: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.nombreCliente) }
                Row { Text(text = "Matrícula: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.matricula) }
                Row { Text(text = "Marca: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.marca) }
                Row { Text(text = "Modelo: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.modelo) }
                Text(
                    text = stringResource(id = R.string.Vehicle_services),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                LazyColumn {
                    if (serviciosDelVehiculo != null) {
                        for (servicio in serviciosDelVehiculo) {
                            item {
                                ElevatedCard (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                ) {
                                    Column (
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Row (
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column {
                                                Text(text = servicio.fecha)

                                            }
                                            Spacer(modifier = Modifier.weight(1f))
                                            IconButton(onClick = {
                                                val servicioParaEnviar = servicio.fecha.replace("/", "-")
                                                navController.navigate("viewServicio/$servicioParaEnviar")
                                            }) {
                                                Icon(
                                                    painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                                    contentDescription = "Ver"
                                                )
                                            }
                                            IconButton(onClick = {
                                                showDeleteAlertDialog = true
                                                deletingServicio = servicio
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Delete,
                                                    contentDescription = "Eliminar",
                                                )
                                            }
                                        }
                                        Text(text = servicio.descripcion, maxLines = 1)
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
                                viewModel.deleteServicio(deletingServicio)
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
    else {
        Scaffold (
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.Info_vehicle),
                    showSettings = false,
                    showBackNavArrow = true,
                    navController = navController
                )
            }
        ) { innerPadding ->
            Row (
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(10.dp)
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ) {
                    Row { Text(text = "Nombre cliente: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.nombreCliente) }
                    Row { Text(text = "Matrícula: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.matricula) }
                    Row { Text(text = "Marca: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.marca) }
                    Row { Text(text = "Modelo: ", fontWeight = FontWeight.Bold); Text(text = vehiculo.modelo) }
                    if (showDeleteAlertDialog) {
                        AlertDialog(
                            title = { Text(text = "¿Estás seguro?") },
                            text = { Text(text = "¿Estás seguro de que deseas eliminar el elemento seleccionado?") },
                            confirmButton = {
                                Button(onClick = {
                                    showDeleteAlertDialog = false
                                    viewModel.deleteServicio(deletingServicio)
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
                Column (
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.Vehicle_services),
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    LazyColumn {
                        if (serviciosDelVehiculo != null) {
                            for (servicio in serviciosDelVehiculo) {
                                item {
                                    ElevatedCard (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(6.dp)
                                    ) {
                                        Column (
                                            modifier = Modifier.padding(10.dp)
                                        ) {
                                            Row (
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Column {
                                                    Text(text = servicio.fecha)

                                                }
                                                Spacer(modifier = Modifier.weight(1f))
                                                IconButton(onClick = {
                                                    val servicioParaEnviar = servicio.fecha.replace("/", "-")
                                                    navController.navigate("viewServicio/$servicioParaEnviar")
                                                }) {
                                                    Icon(
                                                        painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                                        contentDescription = "Ver"
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    showDeleteAlertDialog = true
                                                    deletingServicio = servicio
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Delete,
                                                        contentDescription = "Eliminar",
                                                    )
                                                }
                                            }
                                            Text(text = servicio.descripcion, maxLines = 1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}