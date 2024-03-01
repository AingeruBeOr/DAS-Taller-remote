package com.example.proyecto1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.myComponents.TopBar

@Composable
fun Preferencias(navController: NavController, viewModel: ActivityViewModel) {
    //Input values
    val idiomasPosibles = listOf<String>("Euskera", "Castellano", "Inglés")
    var idiomaSeleccionado by remember {
        mutableStateOf(idiomasPosibles[0])
    }
    // TODO

    Scaffold (
        topBar = {
            TopBar(title = "Preferencias", showSettings = false, navController = navController)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "Elige un idioma")
            for (idioma in idiomasPosibles) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = (idioma == idiomaSeleccionado),
                        onClick = { idiomaSeleccionado = idioma }
                    )
                    Text(
                        text = idioma,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Row {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = {
                    // TODO guardar preferencias
                    navController.popBackStack()
                }) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferenciasPreview() {
    Preferencias(viewModel = ActivityViewModel(), navController = rememberNavController())
}