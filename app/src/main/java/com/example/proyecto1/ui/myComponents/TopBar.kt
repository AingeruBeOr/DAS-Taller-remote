package com.example.proyecto1.ui.myComponents

import androidx.compose.material.icons.Icons
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
fun TopBar(title: String, modifier: Modifier = Modifier, showSettings: Boolean, navController: NavController) {
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
        }
    )
}