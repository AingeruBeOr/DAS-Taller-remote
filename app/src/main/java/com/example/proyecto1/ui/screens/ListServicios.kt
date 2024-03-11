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
import androidx.compose.runtime.MutableState
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
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.ui.myComponents.DeleteAlertDialog

@Composable
fun ListServicios(
    navController: NavController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
) {
    var showDeleteAlertDialog = remember {
        mutableStateOf(false)
    }

    // TODO esto se puede inicializar de otra manera y no a algo random
    var deletingServicio = remember {
        mutableStateOf(Servicio("a", "a", "a"))
    }

    var stateServicios = viewModel.servicios.collectAsState(initial = emptyList())


    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (servicio in stateServicios.value) {
            item {
                ServicioCard(
                    servicio = servicio,
                    navController = navController,
                    deletingServicio = deletingServicio,
                    showDeleteAlertDialog = showDeleteAlertDialog
                )
            }
        }
        // This Spacer allows LazyColumn to scroll down more so that the last card buttons are not
        // behind the '+' button
        item {
            Spacer(modifier = Modifier.padding(bottom = 120.dp))
        }
    }

    if (showDeleteAlertDialog.value) {
        DeleteAlertDialog(
            showDeleteAlertDialog = showDeleteAlertDialog,
            deletingElement = deletingServicio,
            deleteElement = viewModel::deleteServicio
        )
    }
}

@Composable
fun ServicioCard(
    servicio: Servicio,
    navController: NavController,
    deletingServicio: MutableState<Servicio>,
    showDeleteAlertDialog: MutableState<Boolean>
) {
    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = servicio.fecha)
                    Text(text = servicio.matricula)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    val servicioParaEnviar = servicio.fecha.replace("/", "-")
                    navController.navigate("viewServicio/$servicioParaEnviar")
                }) {
                    Icon(painterResource(id = R.drawable.baseline_remove_red_eye_24), contentDescription = "Ver")
                }
                IconButton(
                    onClick = {
                        deletingServicio.value = servicio
                        showDeleteAlertDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Eliminar",
                    )
                }
            }
            Text(text = servicio.descripcion, maxLines = 1)
        }
    }
}
