package com.example.proyecto1.ui.screens

import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente

/**
 * Este Composable define el contenido de la pantalla de añadir un cliente; es decir, un formulario
 * y un par de botones.
 */
@Composable
fun AddCliente(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel,
) {
    // Input values
    // Van a ser rememberSaveable para que al girar la pantalla no se borren del formualario
    var nombre by rememberSaveable {
        mutableStateOf("")
    }
    var telefono by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var latitude by rememberSaveable {
        mutableStateOf("")
    }
    var longitude by rememberSaveable {
        mutableStateOf("")
    }

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(all = 15.dp)
    ) {
        // Campo de texto para añadir el nombre
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = stringResource(id = R.string.name)) },
            leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = "Nombre") },
            modifier = modifierForInputs
        )
        // Campo de texto para añadir el teléfono
        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(text = stringResource(id = R.string.phone)) },
            leadingIcon = { Icon(Icons.Rounded.Phone, contentDescription = "Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = modifierForInputs
        )
        // Campo de texto para añadir el email
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(id = R.string.email)) },
            leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = modifierForInputs
        )
        // Campo de texto para añadir latitud
        TextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text(text = "Latitud") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Place, contentDescription = "latitude") },
            modifier = modifierForInputs
        )
        // Campo de texto para añadir longitud
        TextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text(text = "Longitud") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Place, contentDescription = "longitude") },
            modifier = modifierForInputs
        )
        // Linea para añadir los botones de "Cancelar" y "Guardar"
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifierForInputs
        ) {
            OutlinedButton(onClick = {
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
            Button(onClick = {
                var validCoordinates = true
                try {
                    latitude.toDouble()
                    longitude.toDouble()
                }
                catch (e: NumberFormatException) {
                    validCoordinates = false
                    Toast.makeText(
                        context,
                        "Los números de latitud y longitud no son válidos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (validCoordinates) {
                    try {
                        viewModel.addNewCliente(Cliente(nombre, telefono.toInt(), email), latitude, longitude)
                        navController.popBackStack()
                    }
                    catch (e: NumberFormatException) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.Phone_too_long),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    catch (e: SQLiteConstraintException) {
                        Toast.makeText(
                            context,
                            "Ya existe un usuario con ese nombre",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.Save))
            }
        }
    }
}
