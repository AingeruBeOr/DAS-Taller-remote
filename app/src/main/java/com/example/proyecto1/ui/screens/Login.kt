package com.example.proyecto1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R

@Composable
fun Login(
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
    navController: NavController
){
    var username by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Usuario") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(onClick = {
            val response = viewModel.login(username, password)
            // TODO se ejecuta el toast anterior
            when(response) {
                "User does not exist" -> Toast.makeText(
                    context,
                    "El usuario no existe",
                    Toast.LENGTH_SHORT).show()
                "Incorrect password" -> Toast.makeText(
                    context,
                    "La contraseña es incorrecta",
                    Toast.LENGTH_SHORT).show()
                "true" -> {
                    viewModel.pullUserData(username)
                    // Navigate and remove the previous Composable from the back stack
                    navController.navigate("servicios") {
                        popUpTo(0)
                    }
                }
            }
        }) {
            Text(text = "Login")
        }
        OutlinedButton(onClick = {
            // Navigate and remove the previous Composable from the back stack
            navController.navigate("registration") {
                popUpTo(0)
            }
        }) {
            Text(text = "Registrarse")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
}