package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun AddCliente(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel,
) {
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

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .padding(all = 15.dp)
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = stringResource(id = R.string.name)) },
            leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = "Nombre") },
            modifier = modifierForInputs
        )
        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(text = stringResource(id = R.string.phone)) },
            leadingIcon = { Icon(Icons.Rounded.Phone, contentDescription = "Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = modifierForInputs
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(id = R.string.email)) },
            leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = modifierForInputs
        )
        Row (
            horizontalArrangement = Arrangement.End,
            modifier = modifierForInputs
        ) {
            OutlinedButton(onClick = {
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
            Button(onClick = {
                viewModel.addNewCliente(Cliente(nombre, telefono.toInt(), email))
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Save))
            }
        }
    }
}

/**
@Preview(showBackground = true)
@Composable
fun AddClientePreview() {
    AddCliente(navController = rememberNavController(), viewModel = ActivityViewModel())
}*/