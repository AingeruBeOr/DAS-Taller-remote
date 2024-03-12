package com.example.proyecto1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
    changeLocale: (String) -> Unit,
    changeTheme: (String) -> Unit,
    innerPadding: PaddingValues
) {
    // Input values
    val idiomasPosibles = listOf(
        stringResource(id = R.string.Basque_lang),
        stringResource(id = R.string.Spanish_lang),
        stringResource(id = R.string.Engilsh_lang)
    )
    var idiomaSeleccionado by remember {
        mutableStateOf(idiomasPosibles[0])
    }
    val coloresPosibles = listOf(
        stringResource(id = R.string.Purple),
        stringResource(id = R.string.Blue),
        stringResource(id = R.string.Green)
    )
    var colorSeleccionado by remember {
        mutableStateOf(coloresPosibles[0])
    }

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
        Text(stringResource(id = R.string.Choose_theme))
        for (color in coloresPosibles) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = (color == colorSeleccionado),
                    onClick = { colorSeleccionado = color }
                )
                Text(
                    text = color,
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
                    idiomasPosibles[0] -> changeLocale("eu")
                    idiomasPosibles[1] -> changeLocale("es")
                    idiomasPosibles[2] -> changeLocale("en")
                }
                when (colorSeleccionado) {
                    coloresPosibles[0] -> changeTheme("Purple")
                    coloresPosibles[1] -> changeTheme("Blue")
                    coloresPosibles[2] -> changeTheme("Green")
                }
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Save))
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