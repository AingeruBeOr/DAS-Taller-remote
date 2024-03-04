package com.example.proyecto1

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application class is the parent component of the app, which means that other components can
 * access the dependencies that it provides.
 *
 * We need to create this class to use Hilt
 */
@HiltAndroidApp
class TallerApplication : Application()