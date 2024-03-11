package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.ui.myComponents.DeleteAlertDialog

@Composable
fun ListClientes(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
    navController: NavController
) {
    var showDeleteAlertDialog = remember {
        mutableStateOf(false)
    }

    var deletingCliente = remember {
        mutableStateOf(Cliente("a", 1, email = "1"))
    }

    var listaClientes = viewModel.clientes.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (cliente in listaClientes.value) {
            item {
                ElevatedCard (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column (
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Text(text = cliente.nombre)
                            Text(text = cliente.email)
                            Text(text = cliente.telefono.toString())
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            navController.navigate("viewCliente/${cliente.nombre}")
                        }) {
                            Icon(painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = "Ver")
                        }
                        IconButton(onClick = {
                            deletingCliente.value = cliente
                            showDeleteAlertDialog.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Eliminar",
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDeleteAlertDialog.value) {
        DeleteAlertDialog(
            showDeleteAlertDialog = showDeleteAlertDialog,
            deletingElement = deletingCliente,
            deleteElement = viewModel::deleteCliente
        )
    }
}