package com.example.proyecto1.ui.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.proyecto1.R
import com.example.proyecto1.data.repositories.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class TallerAppWidget: GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Single
    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition


    // PreferencesGlanceStateDefinition (DataStore) keys. This is only use because we need to update
    // the DataStore if we want the widget to change. In fact, for this widget we don't need to
    // store anything related to the state because it is managed by te server.
    // If nothing changes, it will not update because its a bit absurd
    companion object {
        val imageKey = stringPreferencesKey("image")
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        // Get last user from dataStore (not the one from the widget)
        val LAST_USER = stringPreferencesKey("lastUser")
        val username = context.dataStore.data.first()[LAST_USER]
        Log.d("Widget", username ?: "No-user")

        // UI
        provideContent {
            GlanceTheme {
                WidgetContent(username)
                // else TODO
            }
        }
    }

    @Composable
    private fun WidgetContent(username: String?) {
        val context = LocalContext.current
        var url : String? = null
        if (username != null) url = getUrl(username)

        // Get the PreferencesGlanceStateDefinition (DataStore) unique for each widget
        val prefs = currentState<Preferences>()
        val imageString = prefs[imageKey]

        // Base64 String image to Bitmap
        //var imageBitmap : Bitmap? = null
        var imageBitmap by remember {
            mutableStateOf<Bitmap?>(null)
        }
        Log.d("Widget", "BASE64 image size: ${imageString?.length}")
        if (username != null) {
            if (imageString == null) {
                Log.d("Widget", "Image on DataStore is null. Fetching from server")
                LaunchedEffect(url) {
                    val result = context.getImageBitmap(url!!)
                    if (result != null) imageBitmap = result
                }
            }
            else {
                Log.d("Widget", "Decoding BASE64 image")
                val bytes = Base64.decode(imageString, Base64.DEFAULT)
                imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }

        Box (
            modifier = GlanceModifier.fillMaxSize().background(Color.White)
        ) {
            Log.d("Widget", "imageBitmap size $imageBitmap and username $username")
            if (imageBitmap != null && username != null) {
                Image(
                    provider = ImageProvider(imageBitmap!!),
                    contentDescription = "Bar plot",
                    modifier = GlanceModifier.fillMaxSize()
                )
                Image(
                    provider = ImageProvider(R.drawable.round_refresh_24),
                    contentDescription = "Refresh",
                    modifier = GlanceModifier.clickable {
                        actionRunCallback<RefreshAction>()
                    }.fillMaxWidth()
                )
            }
            // Si todavía no se ha registrado un usuario en la aplicación desde que se descargó
            else if (username == null) {
                Column (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = GlanceModifier.fillMaxSize()
                ) {
                    Text(
                        text = "Inicie sesión para poder mostrar el widget",
                        style = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp)
                    )
                }
            }
            // If the image has not been loaded
            else {
                CircularProgressIndicator(
                    modifier = GlanceModifier.fillMaxSize()
                )
            }
        }
    }

    private class RefreshAction : ActionCallback {
        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
            // TODO se puede intentar hacer en un worker a parte porque esto tarda mucho
            updateAppWidgetState(context, glanceId) { prefs ->
                Log.d("Widget", "Refresh button callback executing")
                // Get last user from dataStore (not the one from the widget)
                val LAST_USER = stringPreferencesKey("lastUser")
                val username = context.dataStore.data.first()[LAST_USER]

                // Request image to the server
                val imageBitmap = withContext(Dispatchers.IO) {
                    context.getImageBitmap("http://34.155.61.4/widgetPlots/${username}.png")
                }

                // Bitmap to String
                val baos = ByteArrayOutputStream()
                imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b : ByteArray = baos.toByteArray()
                val imageString = Base64.encodeToString(b, Base64.DEFAULT)
                Log.d("Widget", "BASE64 image size in refresh button: ${imageString.length}")

                prefs[imageKey] = imageString
            }
            TallerAppWidget().update(context, glanceId)
        }
    }

    /**
     * Called when the widget instance is deleted. We can then clean up any ongoing task.
     */
    /*override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
        ImageWorker.cancel(context, glanceId)
    }*/

    private fun getUrl(username: String) : String {
        return "http://34.155.61.4/widgetPlots/${username}.png"
    }

}

/**
 * Use Coil to get the image from the Internet
 *
 * Se hace como función extensión de Context para poder usar 'this' haciendo referencia al
 * contexto
 */
suspend fun Context.getImageBitmap(url: String): Bitmap? {
    val request = ImageRequest.Builder(this)
        .data(url)
        .memoryCachePolicy(CachePolicy.DISABLED)  // Para que no la guarde en caché
        .diskCachePolicy(CachePolicy.DISABLED)    // Para que no la guarde en caché
        .build()
    val result = imageLoader.execute(request)
    Log.d("Widget", "Imagen recuperada")
    return result.drawable?.toBitmapOrNull()
}
