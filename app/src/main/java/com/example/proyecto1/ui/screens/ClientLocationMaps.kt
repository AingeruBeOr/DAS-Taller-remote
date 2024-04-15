package com.example.proyecto1.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun ClientLocationMaps() {
    val singapore = LatLng(1.35, 103.87)
    val uni = LatLng(43.263512, -2.950715)
    GoogleMap(
        modifier = Modifier.fillMaxSize()
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore"
        )
        Marker(
            state = MarkerState(position = uni),
            title = "Uni"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapsPreview() {
    ClientLocationMaps()
}