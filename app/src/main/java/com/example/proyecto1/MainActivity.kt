package com.example.proyecto1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.example.proyecto1.ui.screens.MainView
import com.example.proyecto1.ui.theme.TallerTheme
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * AppCompatActivity extends FragmentActivity which extends ComponentActivity.
 *
 * ComponentActivity has all you need for a Compose-only app.
 * If you need AppCompat APIs, an AndroidView which works with AppCompat or MaterialComponents
 * theme, or you need Fragments then use AppCompatActivity.
 *
 * Además, nosotros vamos a usar AppCompatActiviy para poder usar Hilt (Inyección de dependencias)
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Re-created activities receive the same ActivityViewModel.kt instance created by the first activity.
    private val viewModel: ActivityViewModel by viewModels()

    private val NOTIFICATION_CHANNEL_ID = "1"
    private val NOTIFICATION_ID = 1

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher. You can use either a val, as shown in this snippet,
    // or a lateinit var in your onAttach() or onCreate() method.
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        requestPushNotifcations()
        requestWriteInStoragePermission()

        submitDeviceTokenFCM()

        setContent {
            val storedTheme by viewModel.currentTheme.collectAsState(initial = "Blue")
            TallerTheme (
                selectedPrimaryColor = storedTheme
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(
                        viewModel = viewModel,
                        sendNotification = ::sendDownloadNotification,
                        openDial = ::openDial,
                        changeLocales = ::changeLocales,
                        mailTo = ::mailTo,
                        changeColor = ::changeTheme
                    )
                }
            }
        }
    }

    /**
     * Abrir el dial (marcador de teléfono) con un número de teléfono recibido como
     * parametro.
     */
    fun openDial(tel: Int) {
        // Crear el Intent para abrir el Dial con el número del cliente
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$tel")
        }

        // Iniciamos la actividad que abre el Dial
        startActivity(dialIntent)
    }

    /**
     * Abrir la aplicación por defecto como cliente de correo para enviar un correo a la dirección
     * recibida como parámetro.
     */
    fun mailTo(mail: String) {
        // Crear el Intent para abrir el cliente de correo por defecto
        val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        }

        // Iniciamos la actividad que abre el cliente de correo
        startActivity(mailIntent)
    }

    /**
     * Cambiar los Locales (idiomas) de la aplicación en los propios ajustes de la aplicación.
     */
    fun changeLocales(languageCode: String) {
        // The Locale is saved into App Settings so when the app is started again, the Locale
        // is taken from App Settings
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }

    /**
     * Cambiar el tema de la aplicación.
     * Es necesario hacerlo en el contexto de la actividad porque necesitamos regenerar la actividad
     * para poder cambiar el tema.
     */
    fun changeTheme(theme: String) {
        viewModel.changeColor(theme)
        recreate()
    }


    // --- NOTIFICATIONS ---
    /**
     * This channel will be used to manage notifications when a new element is added.
     *
     * Despite being call on each onCreate(), if the CHANNEL_ID already exists, it doesn't create
     * a new one.
     *
     * Extraido de: https://developer.android.com/develop/ui/views/notifications/channels#CreateChannel
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = getString(R.string.Notification_channel_name)
            val descriptionText = getString(R.string.Notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    /**
     * Mandar una notificación avisando de que el archivo con los servicios semanales se ha
     * descargado correctamente
     */
    fun sendDownloadNotification() {
        var builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.round_car_repair_24)
            .setContentTitle(getString(R.string.Notification_title))
            .setContentText(getString(R.string.Notification_content))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)){
            // Comprobamos si la aplicación tiene permisos para la notificación
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }


    /**
     * Solicitar permisos para enviar notificaciones locales
     *
     * Además es necesario definirlo en el Manifest: https://developer.android.com/develop/ui/views/notifications/notification-permission#declare
     */
    fun requestPushNotifcations() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Solicitar permisos para escribir en el sistema de ficheros del dispositivo.
     *
     * Además, hay que añadir los siguientes permisos en el Manifest:
     * - android.permission.READ_EXTERNAL_STORAGE
     * - android.permission.WRITE_EXTERNAL_STORAGE
     */
    fun requestWriteInStoragePermission() {
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun submitDeviceTokenFCM() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = Firebase.messaging.token.await()
            Log.d("FCM", "Device token $token")
            viewModel.submitDeviceTokenFCM(token)
        }
    }
}
