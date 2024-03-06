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
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showSettings: Boolean,
    showBackNavArrow: Boolean = false,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            if (showSettings) {
                IconButton(onClick = {
                    navController.navigate("preferencias")
                }) {
                    Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Ajustes")
                }
            }
        },
        navigationIcon = {
            if (showBackNavArrow) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}