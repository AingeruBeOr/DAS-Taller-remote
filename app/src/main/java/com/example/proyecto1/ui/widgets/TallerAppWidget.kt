package com.example.proyecto1.ui.widgets

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize

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
        val size = LocalSize.current
        Box (
            modifier = GlanceModifier.fillMaxSize().background(Color.White)
        ) {
            val username = "Aingeru"
            Image(
                provider = getImageProvider("http://34.155.61.4/widgetPlots/${username}.png"),
                contentDescription = "Bar plot"
                //modifier = GlanceModifier.fillMaxSize()
            )
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

    private fun getImageProvider(path: String): ImageProvider {
        Log.d("Widget", "Accessing ${path}")
        return ImageProvider(path.toUri())
    }
}
