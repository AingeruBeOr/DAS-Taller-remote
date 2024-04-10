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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel

@Composable
fun Registration(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel
) {
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
        modifier = androidx.compose.ui.Modifier
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
        Text(text = "Solo puedes regitrate siendo un taller. Si eres un cliente, el taller te proporcionará las credenciales de acceso")
        Button(onClick = {
            val message = viewModel.registerTaller(username, password)
            when (message) {
                "User already exists" -> Toast.makeText(
                    context,
                    "Ya existe un usuario con ese nombre",
                    Toast.LENGTH_SHORT).show()
                "User created" -> {
                    Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    // Navigate and remove the previous Composable from the back stack
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
        }) {
            Text(text = "Reigstrarse")
        }
        OutlinedButton(onClick = {
            // Navigate and remove the previous Composable from the back stack
            navController.navigate("login") {
                popUpTo(0)
            }
        }) {
            Text(text = "Login")
        }
    }
}