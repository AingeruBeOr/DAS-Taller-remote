package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.ui.myComponents.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehiculo(
    navController: NavController,
    viewModel: ActivityViewModel,
    sendNotification: (String) -> Unit
) {
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

    var nombreCliente by remember {
        mutableStateOf("")
    }

    // DropDown Menu state
    var dropdownIsExtended by remember {
        mutableStateOf(false)
    }

    var listaClientes = viewModel.clientes.collectAsState(initial = emptyList()).value

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.TopBarAddVehicle), showSettings = false, navController = navController)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            TextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text(text = stringResource(id = R.string.Plate)) },
                modifier = modifierForInputs
            )
            TextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text(text = stringResource(id = R.string.Brand)) },
                modifier = modifierForInputs
            )
            TextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text(text = stringResource(id = R.string.Model)) },
                modifier = modifierForInputs
            )
            ExposedDropdownMenuBox (
                expanded = dropdownIsExtended,
                onExpandedChange = { dropdownIsExtended = !dropdownIsExtended }
            ) {
                TextField(
                    value = nombreCliente,
                    onValueChange = { nombreCliente = it },
                    label = { Text(stringResource(id = R.string.Client)) },
                    trailingIcon = { Icon(Icons.Rounded.ArrowDropDown, contentDescription = "ArrowDropDown") },
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                )
                ExposedDropdownMenu(
                    expanded = dropdownIsExtended,
                    onDismissRequest = { dropdownIsExtended = false }
                ) {
                    for (cliente in listaClientes) {
                        DropdownMenuItem(
                            text = { Text(text = cliente.nombre) },
                            onClick = { nombreCliente = cliente.nombre; dropdownIsExtended = false }
                        )
                    }
                }
            }
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = modifierForInputs
            ) {
                OutlinedButton(onClick = {
                    navController.popBackStack()
                },) {
                    Text(text = stringResource(id = R.string.Cancel))
                }
                Button(onClick = {
                    viewModel.addNewVehiculo(Vehiculo(matricula, marca, modelo, nombreCliente))
                    sendNotification("vehicle")
                    navController.popBackStack()
                }) {
                    Text(text = stringResource(id = R.string.Save))
                }
            }
        }
    }
}

/**
@Preview(showBackground = true)
@Composable
fun AddVehiculoPreview() {
    AddVehiculo(navController = rememberNavController(), viewModel = ActivityViewModel())
}*/