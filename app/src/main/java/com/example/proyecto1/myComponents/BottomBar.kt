package com.example.proyecto1.myComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomAppBar (actions = {
        IconButton(onClick = {
            navController.navigate("servicios")
        }) {
            Icon(Icons.Rounded.Build, contentDescription = "Servicios")
        }
        IconButton(onClick = {
            navController.navigate("vehículos")
        }) {
            Icon(Icons.Rounded.Home, contentDescription = "Vehículos")
        }
        IconButton(onClick = {
            navController.navigate("clientes")
        }) {
            Icon(Icons.Rounded.Person, contentDescription = "Clientes")
        }
    })
}