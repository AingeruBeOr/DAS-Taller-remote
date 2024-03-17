package com.example.proyecto1.ui.myComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.proyecto1.R


/**
 * This Composable uses the <T> class in order to become generic.
 * In this way, T can be any type of object. In this case, T can be:
 * - Vehiculo
 * - Servicio
 * - Cliente
 *
 * De está manera, conseguimos reducir el código al no tener que implementarlo en cada pantalla
 * donde se usa.
 */
@Composable
fun <T> DeleteAlertDialog(
    showDeleteAlertDialog: MutableState<Boolean>,
    deletingElement: MutableState<T>,
    deleteElement: (T) -> Unit
) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.Delete_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.Delete_dialog_text)) },
        confirmButton = {
            Button(onClick = {
                showDeleteAlertDialog.value = false
                deleteElement(deletingElement.value)
            }) {
                Text(text = stringResource(id = R.string.Confirm))
            }
        },
        dismissButton = {
            Button(onClick = { showDeleteAlertDialog.value = false }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
        },
        onDismissRequest = { showDeleteAlertDialog.value = false },
    )
}