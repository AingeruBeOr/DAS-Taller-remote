package com.example.proyecto1.myComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto1.ActivityViewModel

@Composable
fun ListServicios(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (servicio in viewModel.servicios) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = servicio.descripcion)
                        Text(text = servicio.fecha)
                        Text(text = servicio.matricula)
                    }
                }
            }
        }
    }
}

@Composable
fun ListVehículos(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (vehiculo in viewModel.vehiculos) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = vehiculo.marca)
                        Text(text = vehiculo.modelo)
                        Text(text = vehiculo.matricula)
                    }
                }
            }
        }
    }
}

@Composable
fun ListClientes(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (cliente in viewModel.clientes) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = cliente.nombre)
                        Text(text = cliente.email)
                        Text(text = cliente.telefono.toString())
                    }
                }
            }
        }
    }
}
