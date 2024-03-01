package com.example.proyecto1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.Servicio
import com.example.proyecto1.myComponents.TopBar
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServicio(navController: NavController, viewModel: ActivityViewModel) {
    // Input values
    var descripcion by remember {
        mutableStateOf("")
    }

    var fecha by remember {
        mutableStateOf("11/11/2011")
    }

    var matricula by remember {
        mutableStateOf("")
    }

    val datePickerState = rememberDatePickerState()
    var showDateDialog by remember {
        mutableStateOf(false)
    }

    val modifierForInputs = Modifier.fillMaxWidth()

    Scaffold (
        topBar = {
            TopBar(title = "Añadir nuevo servicio", showSettings = false)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Row {
                Text(text = fecha, modifier = Modifier
                    .padding(10.dp))
                Button(onClick = {
                    showDateDialog = true
                }) {
                    Text(text = "Cambiar fecha")
                }
            }
            TextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text(text = "Matrícula") },
                modifier = modifierForInputs
            )
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text(text = "Descripción") },
                modifier = modifierForInputs
            )
            Row {
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = {
                    viewModel.addNewServicio(Servicio(fecha, matricula, descripcion))
                    navController.popBackStack()
                }) {
                    Text(text = "Guardar")
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
                            Text(text = "Confirmar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddServicioPreview() {
    AddServicio(navController = rememberNavController(), viewModel = ActivityViewModel())
}