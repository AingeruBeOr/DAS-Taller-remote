package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun Preferencias(
    navController: NavController,
    changeLocale: (String) -> Unit
) {
    //Input values
    val idiomasPosibles = listOf(
        stringResource(id = R.string.Basque_lang),
        stringResource(id = R.string.Spanish_lang),
        stringResource(id = R.string.Engilsh_lang))
    var idiomaSeleccionado by remember {
        mutableStateOf(idiomasPosibles[0])
    }
    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.PreferencesTitle), showSettings = false, navController = navController)
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            Text(text = stringResource(id = R.string.LanguageChoose), modifier = Modifier.padding(bottom = 10.dp))
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
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = stringResource(id = R.string.Cancel))
                }
                Button(onClick = {
                    when (idiomaSeleccionado) {
                        "Euskera" -> changeLocale("eu")
                        "Castellano" -> changeLocale("es")
                        "Inglés" -> changeLocale("en")
                    }
                    navController.popBackStack()
                }) {
                    Text(text = stringResource(id = R.string.Save))
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreferenciasPreview() {
    Preferencias(viewModel = ActivityViewModel(), navController = rememberNavController())
}*/