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

/**
 * BOTTOM BAR.
 * This is only seen if one of the main screens is showing
 */
@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomAppBar (actions = {
        IconButton(onClick = {
            // TODO consultar la documentación para hacer esto de una forma más limpia
            navController.popBackStack()
            navController.navigate("servicios")
        }) {
            Icon(Icons.Rounded.Build, contentDescription = "Servicios")
        }
        IconButton(onClick = {
            // TODO consultar la documentación para hacer esto de una forma más limpia
            navController.popBackStack()
            navController.navigate("vehiculos")
        }) {
            Icon(Icons.Rounded.Home, contentDescription = "Vehículos")
        }
        IconButton(onClick = {
            // TODO consultar la documentación para hacer esto de una forma más limpia
            navController.popBackStack()
            navController.navigate("clientes")
        }) {
            Icon(Icons.Rounded.Person, contentDescription = "Clientes")
        }
    })
}