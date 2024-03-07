package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun ViewService(
    navController: NavController,
    viewModel: ActivityViewModel,
    fecha: String?
) {
    var servicio by remember {
        mutableStateOf(Servicio("a", "a", "a"))
    }

    if (fecha != null) {
        servicio = viewModel.getServicioFromFecha(fecha)
    }

    Scaffold (
        topBar = {
            TopBar(
                title = "Información del servicio",
                showSettings = false,
                showBackNavArrow = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(all = 15.dp)
        ) {
            Text(text = servicio.matricula)
            Text(text = servicio.fecha)
            Text(text = servicio.descripcion)
        }
    }
}