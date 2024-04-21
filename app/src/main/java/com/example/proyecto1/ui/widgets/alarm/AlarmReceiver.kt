package com.example.proyecto1.ui.widgets.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.example.proyecto1.data.repositories.PreferencesRepository
import com.example.proyecto1.ui.widgets.TallerAppWidget
import com.example.proyecto1.ui.widgets.getImageBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Widget", "Refresh from alarm scheduling")
        CoroutineScope(Dispatchers.IO).launch {
            if (context != null) {
                val currentUserName = preferencesRepository.getLastUserName()
                // Lo hacemos con withContext es ejecutarlo en un hilo seprado pero no seguir hasta que tengamos el valor
                val imageBitmap = withContext(Dispatchers.IO) {
                    context.getImageBitmap("http://34.155.61.4/widgetPlots/${currentUserName}.png")
                }

                // Bitmap to String
                val baos = ByteArrayOutputStream()
                imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b : ByteArray = baos.toByteArray()
                val imageString = Base64.encodeToString(b, Base64.DEFAULT)
                Log.d("Widget", "BASE64 image size in VM: ${imageString.length}")

                Log.d("ViewModel", "Updating widget")
                // We get the widget manager
                val manager = GlanceAppWidgetManager(context)
                // We get all the glace IDs that are a TallerAppWidget (remember than we can have more
                // than one widget of the same type)
                val glanceIds = manager.getGlanceIds(TallerAppWidget::class.java)
                // For each glanceIds...
                Log.d("Widget", "Los glanceIds de los widgets son: $glanceIds")
                glanceIds.forEach { glanceId ->
                    updateAppWidgetState(context, glanceId) { prefs ->
                        prefs[TallerAppWidget.imageKey] = imageString
                    }
                }
                TallerAppWidget().updateAll(context)
            }
            else {
                Log.e("Widget", "Refresh from alarm scheduling, null context")
            }
        }
    }
}