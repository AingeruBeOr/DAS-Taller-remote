package com.example.proyecto1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.Vehiculo
import com.example.proyecto1.myComponents.TopBar

@Composable
fun AddVehiculo(navController: NavController, viewModel: ActivityViewModel) {
    // Input values
    var matricula by remember {
        mutableStateOf("")
    }

    var marca by remember {
        mutableStateOf("")
    }

    var modelo by remember {
        mutableStateOf("")
    }

    val modifierForInputs = Modifier.fillMaxWidth()

    Scaffold (
        topBar = {
            TopBar(title = "Añadir nuevo vehículo", showSettings = false)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text(text = "Matrícula") },
                modifier = modifierForInputs
            )
            TextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text(text = "Marca") },
                modifier = modifierForInputs
            )
            TextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text(text = "Modelo") },
                modifier = modifierForInputs
            )
            Row {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = {
                    viewModel.addNewVehiculo(Vehiculo(matricula, marca, modelo))
                    navController.popBackStack()
                }) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddVehiculoPreview() {
    AddVehiculo(navController = rememberNavController(), viewModel = ActivityViewModel())
}