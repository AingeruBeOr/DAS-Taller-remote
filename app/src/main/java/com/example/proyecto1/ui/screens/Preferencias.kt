package com.example.proyecto1.ui.screens

import android.content.res.Configuration
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R

@Composable
fun Preferencias(
    navController: NavController,
    changeLocale: (String) -> Unit,
    changeTheme: (String) -> Unit,
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel
) {
    val currentLocale = LocalContext.current.resources.configuration.locales[0]
    val currentColor = viewModel.getThemeSnapshot()

    // Input values
    // Van a ser rememberSaveable para que al girar la pantalla no se borren del formualario
    val idiomasPosibles = listOf(
        stringResource(id = R.string.Basque_lang),
        stringResource(id = R.string.Spanish_lang),
        stringResource(id = R.string.Engilsh_lang)
    )
    var idiomaSeleccionado = rememberSaveable {
        when (currentLocale.toString()) {
            "es" -> mutableStateOf(idiomasPosibles[1])
            "eu" -> mutableStateOf(idiomasPosibles[0])
            "en" -> mutableStateOf(idiomasPosibles[2])
            else -> mutableStateOf(idiomasPosibles[1])
        }
    }
    val coloresPosibles = listOf(
        stringResource(id = R.string.Purple),
        stringResource(id = R.string.Blue),
        stringResource(id = R.string.Green)
    )
    var colorSeleccionado = rememberSaveable {
        Log.d("Color", currentColor)
        when (currentColor) {
            "Blue" -> mutableStateOf(coloresPosibles[1])
            "Purple" -> mutableStateOf(coloresPosibles[0])
            "Green" -> mutableStateOf(coloresPosibles[2])
            else -> mutableStateOf(coloresPosibles[1])
        }
    }

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            LanguageSelector(
                idiomaSeleccionado = idiomaSeleccionado,
                idiomasPosibles = idiomasPosibles
            )
            MainColorSelector(
                coloresPosibles = coloresPosibles,
                colorSeleccionado = colorSeleccionado
            )
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
                    when (idiomaSeleccionado.value) {
                        idiomasPosibles[0] -> changeLocale("eu")
                        idiomasPosibles[1] -> changeLocale("es")
                        idiomasPosibles[2] -> changeLocale("en")
                    }
                    when (colorSeleccionado.value) {
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
    else {
        Row (
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
        ){
            LanguageSelector(
                idiomaSeleccionado = idiomaSeleccionado,
                idiomasPosibles = idiomasPosibles,
                modifier = Modifier.weight(1f)
            )
            Column (
                modifier = Modifier.weight(1f)
            ) {
                MainColorSelector(
                    coloresPosibles = coloresPosibles,
                    colorSeleccionado = colorSeleccionado
                )
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
                        when (idiomaSeleccionado.value) {
                            idiomasPosibles[0] -> changeLocale("eu")
                            idiomasPosibles[1] -> changeLocale("es")
                            idiomasPosibles[2] -> changeLocale("en")
                        }
                        when (colorSeleccionado.value) {
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
    }
}

/**
 * Elemento Composable selector del idioma. Este elemento es una linea de texto y una lista con 3
 * opciones para elegir entre los idiomas disponibles en la aplicación.
 */
@Composable
fun LanguageSelector(
    idiomaSeleccionado: MutableState<String>,
    idiomasPosibles: List<String>,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.LanguageChoose), modifier = Modifier.padding(bottom = 10.dp))
        for (idioma in idiomasPosibles) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = (idioma == idiomaSeleccionado.value),
                    onClick = { idiomaSeleccionado.value = idioma }
                )
                Text(
                    text = idioma,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/**
 * Elemento Composable selector del color. Este elemento es una linea de texto y una lista con 3
 * opciones para elegir entre los colores disponibles en la aplicación.
 */
@Composable
fun MainColorSelector(
    coloresPosibles: List<String>,
    colorSeleccionado: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Text(stringResource(id = R.string.Choose_theme), modifier = Modifier.padding(bottom = 10.dp))
        for (color in coloresPosibles) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = (color == colorSeleccionado.value),
                    onClick = { colorSeleccionado.value = color }
                )
                Text(
                    text = color,
                    modifier = Modifier.padding(8.dp)
                )
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