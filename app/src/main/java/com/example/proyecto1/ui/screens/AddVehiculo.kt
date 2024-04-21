package com.example.proyecto1.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,    /* prefix */
        ".jpg",     /* suffix */
        externalCacheDir  /* directory */
    )
    return image
}

/**
 * Este Composable define el contenido de la pantalla de añadir un vehículo; es decir, un formulario
 * y un par de botones.
 */
@Composable
fun AddVehiculo(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ActivityViewModel
) {
    // Input values
    // Van a ser rememberSaveable para que al girar la pantalla no se borren del formualario
    var matricula by rememberSaveable {
        mutableStateOf("")
    }
    var marca by rememberSaveable {
        mutableStateOf("")
    }
    var modelo by rememberSaveable {
        mutableStateOf("")
    }

    var nombreCliente = remember {
        mutableStateOf("")
    }

    // DropDown Menu state
    var dropdownIsExtended = remember {
        mutableStateOf(false)
    }

    var listaClientes = viewModel.clientes.collectAsState(initial = emptyList()).value

    val context = LocalContext.current

    // Create a temporary file to store the photo taken by the camera
    var file by remember {
        mutableStateOf<File?>(null)
    }
    // Get the uri for the temporary file
    var uri : Uri = Uri.EMPTY

    var fileUploaded by remember {
        mutableStateOf(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { savedInUri ->
        // Callback process when .launch()
        if (savedInUri) {
            fileUploaded = true
            Toast.makeText(context, "Foto cargada", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "No se ha podido cargar la foto", Toast.LENGTH_SHORT).show()
    }

    val modifierForInputs = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(innerPadding)
            .padding(15.dp)
    ) {
        // Campo de texto para introducir la matrícula
        TextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text(text = stringResource(id = R.string.Plate)) },
            modifier = modifierForInputs
        )
        // Campo de texto para introducir la marca
        TextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text(text = stringResource(id = R.string.Brand)) },
            modifier = modifierForInputs
        )
        // Campo de texto para introducir el modelo
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text(text = stringResource(id = R.string.Model)) },
            modifier = modifierForInputs
        )
        ClienteSelector(
            dropdownIsExtended = dropdownIsExtended,
            nombreCliente = nombreCliente,
            listaClientes = listaClientes
        )
        Row {
            Button(onClick = {
                // Create a temporary file to store the photo taken by the camera
                file = context.createImageFile()
                // Get the uri for the temporary file
                uri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    file!!
                )
                cameraLauncher.launch(uri) // 'uri' is the File uri to save camera photo
            }) {
                Text(text = "Foto de la documentación")
            }
            if (fileUploaded) Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Check",
                tint = Color.Green
            )
        }
        Row (
            horizontalArrangement = Arrangement.End,
            modifier = modifierForInputs
        ) {
            OutlinedButton(onClick = {
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
            Button(onClick = {
                viewModel.addNewVehiculo(
                    Vehiculo(matricula, marca, modelo, nombreCliente.value),
                    file!!
                )
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.Save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteSelector(
    dropdownIsExtended: MutableState<Boolean>,
    nombreCliente: MutableState<String>,
    listaClientes: List<Cliente>
) {
    ExposedDropdownMenuBox (
        expanded = dropdownIsExtended.value,
        onExpandedChange = { dropdownIsExtended.value = !dropdownIsExtended.value}
    ) {
        TextField(
            value = nombreCliente.value,
            onValueChange = { nombreCliente.value = it },
            label = { Text(stringResource(id = R.string.Client)) },
            trailingIcon = { Icon(Icons.Rounded.ArrowDropDown, contentDescription = "ArrowDropDown") },
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        ExposedDropdownMenu(
            expanded = dropdownIsExtended.value,
            onDismissRequest = { dropdownIsExtended.value = false }
        ) {
            for (cliente in listaClientes) {
                DropdownMenuItem(
                    text = { Text(text = cliente.nombre) },
                    onClick = { nombreCliente.value = cliente.nombre; dropdownIsExtended.value = false }
                )
            }
        }
    }
}

/**
@Preview(showBackground = true)
@Composable
fun AddVehiculoPreview() {
    AddVehiculo(navController = rememberNavController(), viewModel = ActivityViewModel())
}*/