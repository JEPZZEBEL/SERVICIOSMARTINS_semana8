package com.example.serviciosmartins.ui

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _isAdmin = MutableStateFlow(prefs.getBoolean("is_admin", false))
    val isAdmin: StateFlow<Boolean> = _isAdmin

    fun setAdmin(enabled: Boolean) {
        prefs.edit().putBoolean("is_admin", enabled).apply()
        _isAdmin.value = enabled
    }
}
