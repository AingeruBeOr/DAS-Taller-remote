package com.example.proyecto1.ui.widgets

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.proyecto1.R

class TallerAppWidget: GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Single


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            GlanceTheme {
                WidgetContent()
            }
        }
    }

    @Composable
    private fun WidgetContent() {
        val context = LocalContext.current
        val size = LocalSize.current
        val url = getUrl()
        var image by remember {
            mutableStateOf<Bitmap?>(null)
        }

        // Executed in a coroutine when 'url' changes
        LaunchedEffect(url) {
            image = context.getImageBitmap(url)
        }

        Box (
            modifier = GlanceModifier.fillMaxSize().background(Color.White)
        ) {
            if (image != null) {
                Image(
                    provider = ImageProvider(image!!),
                    contentDescription = "Bar plot",
                    modifier = GlanceModifier.fillMaxSize()
                )
            }
            // If the image has not been loaded
            else {
                CircularProgressIndicator(
                    modifier = GlanceModifier.fillMaxSize()
                )
            }
            Image(
                provider = ImageProvider(R.drawable.round_refresh_24),
                contentDescription = "Refresh",
                modifier = GlanceModifier.fillMaxWidth().clickable {
                    actionRunCallback<RefreshAction>()
                }
            )
        }
    }

    private class RefreshAction : ActionCallback {
        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
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

    private fun getUrl() : String {
        val username = "Aingeru"
        return "http://34.155.61.4/widgetPlots/${username}.png"
    }

    /**
     * Use Coil to get the image from the Internet
     *
     * Se hace como función extensión de Context para poder usar 'this' haciendo referencia al
     * contexto
     */
    private suspend fun Context.getImageBitmap(url: String): Bitmap? {
        val request = ImageRequest.Builder(this)
            .data(url)
            .memoryCachePolicy(CachePolicy.DISABLED)  // Para que no la guarde en caché
            .diskCachePolicy(CachePolicy.DISABLED)    // Para que no la guarde en caché
            .build()
        val result = imageLoader.execute(request)
        return result.drawable?.toBitmapOrNull()
    }
}
