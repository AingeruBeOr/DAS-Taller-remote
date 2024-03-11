package com.example.proyecto1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.ui.AppNavigation
import com.example.proyecto1.ui.myComponents.BottomBar
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun MainView(
    viewModel: ActivityViewModel,
    openDial: (Int) -> Unit,
    mailTo: (String) -> Unit,
    changeLocales: (String) -> Unit,
    sendNotification: (String) -> Unit
) {
    // Defining NavController
    val navController = rememberNavController()
    // Subscribe (observer) to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the route (get only the text before the first '/')
    val currentRoute = navBackStackEntry?.destination?.route?.split("/")?.get(0)

    // Componente layout para incluir barra superior, inferior y botón flotante
    Scaffold (
        // Barra superior
        topBar = {
            TopBar(navController = navController)
        },
        // Barra inferior
        bottomBar = {
            if (currentRoute == "servicios" || currentRoute == "vehiculos" || currentRoute == "clientes") {
                BottomBar(navController = navController)
            }
        },
        floatingActionButton = {
            if (currentRoute == "servicios" || currentRoute == "vehiculos" || currentRoute == "clientes") {
                Column (
                    horizontalAlignment = Alignment.End
                ) {
                    if (currentRoute == "servicios") {
                        SmallFloatingActionButton(
                            onClick = {
                                // TODO
                            },
                            modifier = Modifier.padding(bottom = 5.dp)
                        ) {
                            Icon(painterResource(id = R.drawable.round_download_24), contentDescription = "Añadir")
                        }
                    }
                    FloatingActionButton(onClick = {
                        // Get the current route (current screen)
                        Log.d("Routing", "Current route: $currentRoute")

                        // Change screen depending on currentRoute (current screen)
                        when (currentRoute) {
                            "servicios" -> navController.navigate("newServicio")
                            "vehiculos" -> navController.navigate("newVehiculo")
                            "clientes" -> navController.navigate("newCliente")
                        }
                    }) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Añadir")
                    }
                }
            }
        }
    ) {
        // Contenido principal
        innerPadding  ->
        AppNavigation(
            viewModel = viewModel,
            openDial = openDial,
            mailTo = mailTo,
            changeLocales = changeLocales,
            sendNotification = sendNotification,
            navController = navController,
            innerPadding = innerPadding
        )
    }
}