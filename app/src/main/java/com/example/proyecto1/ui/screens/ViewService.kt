package com.example.proyecto1.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun ViewService(
    navController: NavController,
    viewModel: ActivityViewModel,
    fecha: String?
) {
    var servicio by remember {
        mutableStateOf(Servicio("a", "a", "a"))
    }

    if (fecha != null) {
        servicio = viewModel.getServicioFromFecha(fecha)
    }

    Scaffold (
        topBar = {
            TopBar(
                title = stringResource(id = R.string.Info_service),
                showSettings = false,
                showBackNavArrow = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            PortraitLayout(servicio = servicio, innerPadding = innerPadding)
        }
        else {
            LandscapeLayout(servicio = servicio, innerPadding = innerPadding)
        }
    }
}

@Composable
fun PortraitLayout(
    servicio: Servicio,
    innerPadding: PaddingValues
) {
    Column (
        modifier = Modifier
            .padding(innerPadding)
            .padding(all = 15.dp)
    ) {
        Text(text = stringResource(id = R.string.Client) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.matricula, modifier = Modifier.padding(bottom = 10.dp))
        Text(text = stringResource(id = R.string.Date) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.fecha, modifier = Modifier.padding(bottom = 10.dp))
        Text(text = stringResource(id = R.string.Description) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.descripcion)
    }
}

@Composable
fun LandscapeLayout(
    servicio: Servicio,
    innerPadding: PaddingValues
) {
    Row (
        modifier = Modifier
            .padding(innerPadding)
            .padding(all = 15.dp)
    ) {
        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(id = R.string.Plate) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = servicio.matricula, modifier = Modifier.padding(bottom = 10.dp))
            Text(text = stringResource(id = R.string.Date) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = servicio.fecha, modifier = Modifier.padding(bottom = 10.dp))
        }
        Column (
            modifier = Modifier.weight(2f)
        ) {
            Text(text = stringResource(id = R.string.Description) + ":", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = servicio.descripcion)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewServicePreview() {
    val servicio = Servicio(
        fecha = "15/1/3021",
        descripcion = "Le he hecho un cambio de aceite porque ya le tocaba de hacía tiempo",
        matricula = "1234ABC"
    )
    Column (
        modifier = Modifier
            .padding(all = 15.dp)
    ) {
        Text(text = "Cliente:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.matricula, modifier = Modifier.padding(bottom = 10.dp))
        Text(text = "Fecha:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.fecha, modifier = Modifier.padding(bottom = 10.dp))
        Text(text = "Descripción:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = servicio.descripcion)
    }
}