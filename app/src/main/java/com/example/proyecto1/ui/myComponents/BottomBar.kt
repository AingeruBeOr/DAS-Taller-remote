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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyecto1.R

/**
 * BOTTOM BAR.
 * This is only seen if one of the main screens is showing
 */
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectedScreen: Int
) {

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Build, contentDescription = "Servicios") },
            label = { Text(text = stringResource(id = R.string.ServicesTitle)) },
            selected = (selectedScreen == 0),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("servicios")
            }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.round_car_repair_24), contentDescription = "Vehículos") },
            label = { Text(text = stringResource(id = R.string.VehiclesTitle)) },
            selected = (selectedScreen == 1),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("vehiculos")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Person, contentDescription = "Clientes") },
            label = { Text(text = stringResource(id = R.string.ClientsTitle)) },
            selected = (selectedScreen == 2),
            onClick = {
                // TODO consultar la documentación para hacer esto de una forma más limpia
                navController.popBackStack()
                navController.navigate("clientes")
            }
        )
    }
}