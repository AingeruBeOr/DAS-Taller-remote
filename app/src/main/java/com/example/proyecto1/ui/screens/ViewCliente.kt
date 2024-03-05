package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun ViewCliente(
    navController: NavController,
    viewModel: ActivityViewModel,
    nombreCliente: String?
) {
    var cliente by remember {
        mutableStateOf(Cliente("a", 1, "1"))
    }

    if (nombreCliente != null) {
        cliente = viewModel.getUserDataFromName(nombreCliente)
    }

    Scaffold (
        topBar = {
            TopBar(
                title = "Información del cliente",
                showSettings = false,
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
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Llamar")
                }
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 10.dp)
            )  {
                Icon(Icons.Rounded.Email, contentDescription = "Email")
                Text(text = cliente.email)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Escribir correo")
                }
            }
            Text(text = "Vehículos del cliente")
            LazyColumn {

            }
        }
    }
}