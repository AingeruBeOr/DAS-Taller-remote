package com.example.proyecto1.ui.myComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyecto1.R

/**
 * BOTTOM BAR.
 * This is only seen if one of the main screens is showing
 */
@Composable
fun BottomBar(navController: NavController) {
    // Subscribe (observer) to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Build, contentDescription = "Servicios") },
            label = { Text(text = stringResource(id = R.string.ServicesTitle)) },
            selected = (currentRoute == "servicios"),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("servicios")
            }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.round_car_repair_24), contentDescription = "Vehículos") },
            label = { Text(text = stringResource(id = R.string.VehiclesTitle)) },
            selected = (currentRoute == "vehiculos"),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("vehiculos")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Person, contentDescription = "Clientes") },
            label = { Text(text = stringResource(id = R.string.ClientsTitle)) },
            selected = (currentRoute == "clientes"),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("clientes")
            }
        )
    }
}