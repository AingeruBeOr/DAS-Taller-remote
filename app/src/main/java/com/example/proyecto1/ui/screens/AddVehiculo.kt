package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo

/**
 * Este Composable define el contenido de la pantalla de añadir un vehículo; es decir, un formulario
 * y un par de botones.
 */
@Composable
fun AddVehiculo(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel
) {
    // Input values
    // Van a ser rememberSaveable para que al girar la pantalla no se borren del formualario
    var matricula by rememberSaveable {
        mutableStateOf("")
    }
    var marca by rememberSaveable {
        mutableStateOf("")
    }
    var modelo by rememberSaveable {
        mutableStateOf("")
    }

    var nombreCliente = remember {
        mutableStateOf("")
    }

    // DropDown Menu state
    var dropdownIsExtended = remember {
        mutableStateOf(false)
    }

    var listaClientes = viewModel.clientes.collectAsState(initial = emptyList()).value

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .padding(15.dp)
    ) {
        // Campo de texto para introducir la matrícula
        TextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text(text = stringResource(id = R.string.Plate)) },
            modifier = modifierForInputs
        )
        // Campo de texto para introducir la marca
        TextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text(text = stringResource(id = R.string.Brand)) },
            modifier = modifierForInputs
        )
        // Campo de texto para introducir el modelo
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text(text = stringResource(id = R.string.Model)) },
            modifier = modifierForInputs
        )
        ClienteSelector(
            dropdownIsExtended = dropdownIsExtended,
            nombreCliente = nombreCliente,
            listaClientes = listaClientes
        )
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
                viewModel.addNewVehiculo(Vehiculo(matricula, marca, modelo, nombreCliente.value))
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteSelector(
    dropdownIsExtended: MutableState<Boolean>,
    nombreCliente: MutableState<String>,
    listaClientes: List<Cliente>
) {
    ExposedDropdownMenuBox (
        expanded = dropdownIsExtended.value,
        onExpandedChange = { dropdownIsExtended.value = !dropdownIsExtended.value}
    ) {
        TextField(
            value = nombreCliente.value,
            onValueChange = { nombreCliente.value = it },
            label = { Text(stringResource(id = R.string.Client)) },
            trailingIcon = { Icon(Icons.Rounded.ArrowDropDown, contentDescription = "ArrowDropDown") },
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        ExposedDropdownMenu(
            expanded = dropdownIsExtended.value,
            onDismissRequest = { dropdownIsExtended.value = false }
        ) {
            for (cliente in listaClientes) {
                DropdownMenuItem(
                    text = { Text(text = cliente.nombre) },
                    onClick = { nombreCliente.value = cliente.nombre; dropdownIsExtended.value = false }
                )
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