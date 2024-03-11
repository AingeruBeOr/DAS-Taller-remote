package com.example.proyecto1.ui.myComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyecto1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    // Subscribe (observer) to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the route (get only the text before the first '/')
    val currentRoute = navBackStackEntry?.destination?.route?.split("/")?.get(0)

    var titleText = remember {
        mutableStateOf("")
    }
    var showSettings = remember {
        mutableStateOf(false)
    }
    var showBackNavArrow = remember {
        mutableStateOf(false)
    }

    when (currentRoute) {
        "servicios" -> {
            titleText.value = stringResource(id = R.string.ServicesTitle)
            showSettings.value = true
            showBackNavArrow.value = false
        }
        "vehiculos" -> {
            titleText.value = stringResource(id = R.string.VehiclesTitle)
            showSettings.value = true
            showBackNavArrow.value = false
        }
        "clientes" -> {
            titleText.value = stringResource(id = R.string.ClientsTitle)
            showSettings.value = true
            showBackNavArrow.value = false
        }
        "newServicio" -> {
            titleText.value = stringResource(id = R.string.TopBarAddService)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "newVehiculo" -> {
            titleText.value = stringResource(id = R.string.TopBarAddVehicle)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "newCliente" -> {
            titleText.value = stringResource(id = R.string.TopBarAddClient)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "preferencias" -> {
            titleText.value = stringResource(id = R.string.PreferencesTitle)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "viewCliente" -> {
            titleText.value = stringResource(id = R.string.Info_client)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "viewVehiculo" -> {
            titleText.value = stringResource(id = R.string.Info_vehicle)
            showSettings.value = false
            showBackNavArrow.value = true
        }
        "viewServicio" -> {
            titleText.value = stringResource(id = R.string.Info_service)
            showSettings.value = false
            showBackNavArrow.value = true
        }
    }

    TopAppBar(
        title = {
            Text(text = titleText.value)
        },
        actions = {
            if (showSettings.value) {
                IconButton(onClick = {
                    navController.navigate("preferencias")
                }) {
                    Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Ajustes")
                }
            }
        },
        navigationIcon = {
            if (showBackNavArrow.value) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}
