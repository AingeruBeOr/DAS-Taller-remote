package com.example.proyecto1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.Cliente
import com.example.proyecto1.myComponents.TopBar

@Composable
fun AddCliente(navController: NavController, viewModel: ActivityViewModel) {
    // Input values
    var nombre by remember {
        mutableStateOf("")
    }

    var telefono by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    val modifierForInputs = Modifier.fillMaxWidth()

    Scaffold (
        topBar = {
            TopBar(title = "Añadir nuevo cliente", showSettings = false, navController = navController)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(text = "Nombre") },
                modifier = modifierForInputs
            )
            TextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text(text = "Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = modifierForInputs
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = modifierForInputs
            )
            Row {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = {
                    viewModel.addNewCliente(Cliente(nombre, telefono.toInt(), email))
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
fun AddClientePreview() {
    AddCliente(navController = rememberNavController(), viewModel = ActivityViewModel())
}