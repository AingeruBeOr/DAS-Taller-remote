package com.example.proyecto1.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.R

// --- COLOR SCHEMES ---
// used tool: https://m3.material.io/theme-builder#/custom
private val PurpleDarkColorScheme = darkColorScheme(
    primary = Purple80,
)

private val PurpleLightColorScheme = lightColorScheme(
    primary = Purple40,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val BlueDarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = Color.White,
    primaryContainer = BluePrimaryDark,
    surface = BlueSurfaceDark,
    secondaryContainer = BlueSecondaryContainerDark
)

private val BlueLightColorScheme = lightColorScheme(
    primary = BluePrimaryLight,
    surface = BlueSurfaceLight,
    primaryContainer = BlueSurfaceLight,
    secondaryContainer = BlueSecondaryContainerLight
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = GreenDarkPrimary,
    surface = GreenDarkSurface,
    primaryContainer = GreenDarkPrimary,
    secondaryContainer = GreenDarkSecondaryContainer
)

private val GreenLightColorScheme = lightColorScheme(
    primary = GreenLigthPrimary,
    surface = GreenLightSurface,
    primaryContainer = GreenLightSurface,
    secondaryContainer = GreenLightSecondaryContainer
)

/**
 * Where are not going to use Dynamic Color (Material You). More info: https://m3.material.io/styles/color/dynamic/choosing-a-source
 */
@Composable
fun TallerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    selectedPrimaryColor: String = "Blue",
    content: @Composable () -> Unit
) {
    /*
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }*/
    val colorScheme =
        when (selectedPrimaryColor) {
            "Purple" -> {
                if (darkTheme) PurpleDarkColorScheme else PurpleLightColorScheme
            }
            "Blue" -> {
                if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
            }
            "Green" -> {
                if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
            }
            else -> {
                if (darkTheme) PurpleDarkColorScheme else PurpleLightColorScheme
            }
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}




// --- PREVIEWS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen() {
    val navController = rememberNavController()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Título de prueba")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("preferencias")
                    }) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Ajustes")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Build, contentDescription = "Servicios") },
                    label = { Text(text = stringResource(id = R.string.ServicesTitle)) },
                    selected = true,
                    onClick = {
                        // Navigate and remove the previous Composable from the back stack
                        navController.navigate("servicios") {
                            popUpTo(0)
                        }
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(R.drawable.round_car_repair_24),
                            contentDescription = "Vehículos"
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.VehiclesTitle)) },
                    selected = false,
                    onClick = {
                        // Navigate and remove the previous Composable from the back stack
                        navController.navigate("vehiculos") {
                            popUpTo(0)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Person, contentDescription = "Clientes") },
                    label = { Text(text = stringResource(id = R.string.ClientsTitle)) },
                    selected = false,
                    onClick = {
                        // Navigate and remove the previous Composable from the back stack
                        navController.navigate("clientes") {
                            popUpTo(0)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                SmallFloatingActionButton(
                    onClick = { },
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.round_download_24),
                        contentDescription = "Añadir"
                    )
                }
                FloatingActionButton(onClick = { }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Añadir")
                }
            }
        }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Botón normal")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Botón de cancelar")
            }
            ElevatedCard (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column (
                        modifier = Modifier.padding(all = 10.dp)
                    ) {
                        Text(text = "Mercedes")
                        Text(text = "Volador")
                        Text(text = "1234ABC")
                        Text(text = "Pepito")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_remove_red_eye_24),
                            contentDescription = "Ver"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Eliminar",
                        )
                    }
                }
            }
            Column {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = true,
                        onClick = { }
                    )
                    Text(
                        text = "Euskera",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurpleLightColorSchemePreview() {
    MaterialTheme(
        colorScheme = PurpleLightColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}

@Preview(showBackground = true)
@Composable
fun PurpleDarkSchemePreview() {
    MaterialTheme(
        colorScheme = PurpleDarkColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}

@Preview(showBackground = true)
@Composable
fun BlueLightSchemePreview() {
    MaterialTheme(
        colorScheme = BlueLightColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}

@Preview(showBackground = true)
@Composable
fun BlueDarkSchemePreview() {
    MaterialTheme(
        colorScheme = BlueDarkColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreenLightSchemePreview() {
    MaterialTheme(
        colorScheme = GreenLightColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreenDarkSchemePreview() {
    MaterialTheme(
        colorScheme = GreenDarkColorScheme,
        typography = Typography,
        content = { DemoScreen() }
    )
}