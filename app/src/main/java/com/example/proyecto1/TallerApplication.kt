package com.example.proyecto1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application class is the parent component of the app, which means that other components can
 * access the dependencies that it provides.
 */
@HiltAndroidApp
class TallerApplication : Application()