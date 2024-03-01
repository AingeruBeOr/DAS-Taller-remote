package com.example.proyecto1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.proyecto1.myComponents.TopBar

@Composable
fun AddCliente() {
    var nombre by remember {
        mutableStateOf("")
    }

    Scaffold (
        topBar = {
            TopBar(title = "Añadir nuevo cliente", showSettings = false)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(text = "Nombre") }
            )
            Text(text = "Nuevo texto")
            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClientePreview() {
    AddCliente()
}