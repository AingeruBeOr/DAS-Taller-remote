package com.example.proyecto1.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.ui.AppNavigation
import com.example.proyecto1.ui.myComponents.BottomBar
import com.example.proyecto1.ui.myComponents.TopBar
import kotlinx.coroutines.launch

/**
 * Este Composable será el primero en llamarse. Contiene el elemento Scaffold que es la estructura
 * principal de la aplicación. El Scaffold incluye (los que se han usado):
 * - Barra supererior [TopBar]
 * - Barra de navegación inferior [BottomBar]
 * - Botón flotante
 * - Contenido principal
 *
 * Estos elementos del Scaffold aparecen y desaparecen dependiendo de la pantalla en la que nos
 * encontremos.
 *
 * En el contenido principal del Scaffold, se incluye el [AppNavigation] que irá construyendo
 * diferentes pantallas que afectarán a los componentes del Scaffold.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainView(
    viewModel: ActivityViewModel,
    openDial: (Int) -> Unit,
    mailTo: (String) -> Unit,
    changeLocales: (String) -> Unit,
    sendNotification: () -> Unit,
    changeColor: (String) -> Unit
) {
    // Defining NavController
    val navController = rememberNavController()
    // Subscribe (observer) to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the route (get only the text before the first '/')
    val currentRoute = navBackStackEntry?.destination?.route?.split("/")?.get(0)

    val corutineScope = rememberCoroutineScope()

    // Componente layout para incluir barra superior, inferior y botón flotante
    Scaffold (
        // Barra superior
        topBar = {
            TopBar(navController = navController)
        },
        // Barra inferior
        bottomBar = {
            // Se mostrará o no dependiendo de la ruta (pantalla actual)
            if (currentRoute == "servicios" || currentRoute == "vehiculos" || currentRoute == "clientes") {
                BottomBar(navController = navController)
            }
        },
        // Botón flotante
        floatingActionButton = {
            // Solo se mostrará el botón de añadir si es un usuario tipo "taller", no "cliente"
            if (viewModel.currentUserType.value == "taller") {
                // Se mostrará o no dependiendo de la ruta (pantalla actual)
                if (currentRoute == "servicios" || currentRoute == "vehiculos" || currentRoute == "clientes") {
                    Column (
                        horizontalAlignment = Alignment.End
                    ) {
                        // Botón pequeño para descargar los servicios de mes
                        if (currentRoute == "servicios") {
                            SmallFloatingActionButton(
                                onClick = {
                                    corutineScope.launch {
                                        viewModel.downloadMonthServices()
                                    }
                                    sendNotification()
                                },
                                modifier = Modifier.padding(bottom = 5.dp)
                            ) {
                                Icon(painterResource(id = R.drawable.round_download_24), contentDescription = "Añadir")
                            }
                        }
                        // Botón pequeño para mostrar la ubicación de los clientes
                        if (currentRoute == "clientes") {
                            SmallFloatingActionButton(
                                onClick = {
                                    navController.navigate("clientMap")
                                },
                                modifier = Modifier.padding(bottom = 5.dp)
                            ) {
                                Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = "Añadir")
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
        }
    ) {
        // Contenido principal
        innerPadding  ->
        AppNavigation(
            viewModel = viewModel,
            openDial = openDial,
            mailTo = mailTo,
            changeLocales = changeLocales,
            navController = navController,
            innerPadding = innerPadding,
            changeColor = changeColor
        )
    }
}