package com.example.proyecto1.ui.widgets

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.unit.ColorProvider
import coil.imageLoader
import coil.request.ImageRequest
import com.example.proyecto1.R
import kotlinx.coroutines.coroutineScope
import java.net.URL

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
                /*Image(
                    provider = ImageProvider(R.mipmap.ic_launcher),
                    contentDescription = "Bar plot",
                    modifier = GlanceModifier.fillMaxSize()
                )*/
                Image(
                    provider = ImageProvider(image!!),
                    contentDescription = "Bar plot",
                    modifier = GlanceModifier.fillMaxSize()
                )
            }
            else {
                CircularProgressIndicator()
            }
            Button(text = "Referesh", onClick = { /*TODO*/ })
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

    private fun getImageProvider(path: String): ImageProvider {
        Log.d("Widget", "Accessing ${path}")
        return ImageProvider(path.toUri())
    }

    /**
     * Use Coil to get the image from the Internet
     *
     * Se hace como función extensión de Context para poder usar 'this' haciendo referencia al
     * contexto
     */
    private suspend fun Context.getImageBitmap(url: String): Bitmap? {
        val request = ImageRequest.Builder(this).data(url).build()
        val result = imageLoader.execute(request)
        return result.drawable?.toBitmapOrNull()
    }
}
