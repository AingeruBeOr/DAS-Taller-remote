package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyecto1.ActivityViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ClientLocationMaps(
    viewModel: ActivityViewModel
) {
    viewModel.getUsersClientLocations()
    val locations = viewModel.userClientLocations.collectAsState()
    val cameraPosition = LatLng(43.32303340840012, -2.42273105891604)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPosition, 8f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        for (location in locations.value) {
            Marker(
                state = MarkerState(position = LatLng(location.latitude.toDouble(), location.longitude.toDouble())),
                title = location.nombre
            )
        }
    }
}
