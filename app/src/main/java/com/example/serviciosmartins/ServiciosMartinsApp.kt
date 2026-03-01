package com.example.serviciosmartins

import android.app.Application
import timber.log.Timber

/**
 * Application class para inicializar utilidades globales.
 * - Timber: logging estructurado para debugging.
 * - LeakCanary: se activa automáticamente en debug (por dependencia).
 */
class ServiciosMartinsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // En debug Timber imprime en Logcat. En release podrías enviar logs a Crashlytics.
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("ServiciosMartinsApp inicializada")
    }
}
