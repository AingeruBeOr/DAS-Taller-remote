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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Este Composable define el contenido de la pantalla de añadir un servicio; es decir, un formulario
 * y un par de botones.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServicio(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel
) {
    val context = LocalContext.current
    // Input values
    // Van a ser rememberSaveable para que al girar la pantalla no se borren del formualario
    var descripcion by rememberSaveable {
        mutableStateOf("")
    }
    var fecha by rememberSaveable {
        mutableStateOf("11/11/2011")
    }
    var matricula = rememberSaveable {
        mutableStateOf("")
    }

    val datePickerState = rememberDatePickerState()
    var showDateDialog by remember {
        mutableStateOf(false)
    }

    var dropdownIsExtended = remember {
        mutableStateOf(false)
    }

    var listaVehiculos = viewModel.vehiculos.collectAsState(initial = emptyList()).value

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .padding(15.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = fecha, modifier = Modifier
                .padding(10.dp))
            Button(onClick = {
                showDateDialog = true
            }) {
                Text(text = stringResource(id = R.string.Change_date))
            }
        }
        // Selector de matrícula tipo ComboBox
        MatriculaSelector(
            dropdownIsExtended = dropdownIsExtended,
            matricula = matricula,
            listaVehiculos = listaVehiculos
        )
        // Campo de texto para añadir una descripción
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text(text = stringResource(id = R.string.Description)) },
            modifier = modifierForInputs,
            maxLines = 10
        )
        Row (
            horizontalArrangement = Arrangement.End,
            modifier = modifierForInputs
        ) {
            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
            Button(onClick = {
                viewModel.addNewServicio(
                    Servicio(fecha = fecha, descripcion = descripcion, matricula = matricula.value),
                    context
                )
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Save))
            }
        }
        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                confirmButton = {
                    Button(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        millis?.let {
                            val formatter = SimpleDateFormat("dd/MM/yyyy")
                            fecha = formatter.format(Date(millis))
                        }
                        showDateDialog = false
                    }) {
                        Text(text = stringResource(id = R.string.Confirm))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatriculaSelector(
    dropdownIsExtended: MutableState<Boolean>,
    matricula: MutableState<String>,
    listaVehiculos: List<Vehiculo>
) {
    ExposedDropdownMenuBox (
        expanded = dropdownIsExtended.value,
        onExpandedChange = { dropdownIsExtended.value = !dropdownIsExtended.value }
    ) {
        TextField(
            value = matricula.value,
            onValueChange = { matricula.value = it },
            label = { Text(stringResource(id = R.string.Plate)) },
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
            for (vehiculo in listaVehiculos) {
                DropdownMenuItem(
                    text = { Text(text = vehiculo.matricula) },
                    onClick = { matricula.value = vehiculo.matricula; dropdownIsExtended.value = false }
                )
            }
        }
    }
}

/**
@Preview(showBackground = true)
@Composable
fun AddServicioPreview() {
    AddServicio(navController = rememberNavController(), viewModel = ActivityViewModel())
}*/