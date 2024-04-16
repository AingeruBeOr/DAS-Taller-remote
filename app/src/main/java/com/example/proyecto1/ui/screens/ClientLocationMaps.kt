package com.example.proyecto1.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.proyecto1.ActivityViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ClientLocationMaps(
    viewModel: ActivityViewModel
) {
    val context = LocalContext.current
    var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // Check if permissions granted
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        val requestPermissionLauncher =
            rememberLauncherForActivityResult( // The same as 'registerForActivityResult' in non-composable environment
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
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        return
    }

    lateinit var lastUserLocaction : Location
    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) lastUserLocaction = location
    }

    viewModel.getUsersClientLocations()
    val locations = viewModel.userClientLocations.collectAsState()
    val cameraPosition = LatLng(43.32303340840012, -2.42273105891604)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPosition, 8f)
    }

    var markerIsCliked by remember {
        mutableStateOf(false)
    }
    var clickedMarkerLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true              // show user location
            )
        ) {
            for (location in locations.value) {
                Marker(
                    state = MarkerState(position = LatLng(location.latitude.toDouble(), location.longitude.toDouble())),
                    title = location.nombre,
                    onClick = {
                        //markerIsCliked = true
                        //clickedMarkerLatLng = it.position
                        false
                    }
                )
            }
        }
    }
}
