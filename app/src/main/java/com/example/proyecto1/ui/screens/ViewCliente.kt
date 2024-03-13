package com.example.proyecto1.ui.screens

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.ui.myComponents.DeleteAlertDialog

data class ClientInfoState(
    val navController: NavController,
    val viewModel: ActivityViewModel,
    val openDial: (Int) -> Unit,
    val sendMail: (String) -> Unit,
    var cliente: Cliente = Cliente("a", 1, "1"),
    val nombreCliente: String?,
    var vehiculosDelCliente: List<Vehiculo>?,
    val deletingVehiculo: MutableState<Vehiculo>,
    val showDeleteAlertDialog: MutableState<Boolean>
)

@Composable
fun ViewCliente(
    navController: NavController,
    viewModel: ActivityViewModel,
    nombreCliente: String?,
    openDial: (Int) -> Unit,
    sendMail: (String) -> Unit,
    innerPadding: PaddingValues
) {
    val state by remember {
        mutableStateOf(
            ClientInfoState(
                navController = navController,
                viewModel = viewModel,
                nombreCliente = nombreCliente,
                openDial = openDial,
                sendMail = sendMail,
                deletingVehiculo = mutableStateOf(
                    Vehiculo(
                        matricula = "1234abc", marca = "Mercedes", modelo = "A45 AMG", nombreCliente = "Pepe")
                ),
                showDeleteAlertDialog = mutableStateOf(false),
                vehiculosDelCliente = emptyList()
            )
        )
    }

    if (nombreCliente != null) {
        state.cliente = viewModel.getUserDataFromName(nombreCliente)
        state.vehiculosDelCliente = viewModel.getClientVehicles(nombreCliente).collectAsState(initial = emptyList()).value
    }

    Log.d("Vehiculos", "Número de vehículos: ${state.vehiculosDelCliente?.size}")

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitLayout(innerPadding = innerPadding, state = state)
    }
    else LandscapeLayout(innerPadding = innerPadding, state = state)
    if (state.showDeleteAlertDialog.value) {
        DeleteAlertDialog(
            showDeleteAlertDialog = state.showDeleteAlertDialog,
            deleteElement = viewModel::deleteVehiculo,
            deletingElement = state.deletingVehiculo
        )
    }
}

@Composable
fun PortraitLayout(
    innerPadding: PaddingValues,
    state: ClientInfoState
) {
    Column (
        modifier = Modifier
            .padding(innerPadding)
            .padding(all = 15.dp)
    ) {
        ClientInfo(cliente = state.cliente, openDial = state.openDial, sendMail = state.sendMail)
        Text(
            text = stringResource(id = R.string.Client_vehicles),
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        VehiculosDelCliente(
            vehiculosDelCliente = state.vehiculosDelCliente,
            navController = state.navController,
            showDeleteAlertDialog = state.showDeleteAlertDialog,
            deletingVehiculo = state.deletingVehiculo
        )

    }
}

@Composable
fun LandscapeLayout(
    innerPadding: PaddingValues,
    state: ClientInfoState
) {
    Row (
        modifier = Modifier
            .padding(innerPadding)
            .padding(10.dp)
    ) {
        ClientInfo(cliente = state.cliente, openDial = state.openDial, sendMail = state.sendMail)
        Column {
            Text(
                text = stringResource(id = R.string.Client_vehicles),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            VehiculosDelCliente(
                vehiculosDelCliente = state.vehiculosDelCliente,
                navController = state.navController,
                showDeleteAlertDialog = state.showDeleteAlertDialog,
                deletingVehiculo = state.deletingVehiculo
            )
        }
    }
}

@Composable
fun ClientInfo(
    cliente: Cliente,
    openDial: (Int) -> Unit,
    sendMail: (String) -> Unit
) {
    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Icon(Icons.Rounded.Person, contentDescription = "Nombre")
            Text(text = cliente.nombre, modifier = Modifier.padding(start = 10.dp))
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Icon(Icons.Rounded.Phone, contentDescription = "Teléfono")
            Text(text = cliente.telefono.toString(), modifier = Modifier.padding(start = 10.dp))
            Button(onClick = {
                openDial(cliente.telefono)
            },
                modifier = Modifier.padding(start = 10.dp)) {
                Icon(painterResource(id = R.drawable.call_outcoming_icon), contentDescription = "Call")
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 10.dp)
        )  {
            Icon(Icons.Rounded.Email, contentDescription = "Email")
            Text(text = cliente.email, modifier = Modifier.padding(start = 10.dp))
            Button(onClick = {
                sendMail(cliente.email)
            }, modifier = Modifier.padding(start = 10.dp)) {
                Icon(Icons.Rounded.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun VehiculosDelCliente(
    vehiculosDelCliente: List<Vehiculo>?,
    navController: NavController,
    showDeleteAlertDialog: MutableState<Boolean>,
    deletingVehiculo: MutableState<Vehiculo>
) {
    LazyColumn {
        if (vehiculosDelCliente != null) {
            for (vehiculo in vehiculosDelCliente) {
                item {
                    VehicleCard(
                       vehiculo = vehiculo,
                       navController = navController,
                       showDeleteAlertDialog = showDeleteAlertDialog,
                       deletingVehiculo = deletingVehiculo
                   )
                }
            }
        }
    }
}

@Composable
fun VehicleCard(
    vehiculo: Vehiculo,
    navController: NavController,
    showDeleteAlertDialog: MutableState<Boolean>,
    deletingVehiculo: MutableState<Vehiculo>
) {
    ElevatedCard (
        modifier = Modifier
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
                showDeleteAlertDialog.value = true
                deletingVehiculo.value = vehiculo
            }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Eliminar",
                )
            }
        }
    }
}
