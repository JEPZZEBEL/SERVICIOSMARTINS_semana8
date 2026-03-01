package com.example.serviciosmartins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciosmartins.data.AppDatabase
import com.example.serviciosmartins.repo.ServiceRepository
import com.example.serviciosmartins.ui.AppNav
import com.example.serviciosmartins.ui.ServicesViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Timber para logs (debugging)
        Timber.plant(Timber.DebugTree())

        // ✅ Room + Repo
        val db = AppDatabase.getInstance(applicationContext)
        val repo = ServiceRepository(db.serviceDao())

        // ✅ Factory para ViewModel con constructor
        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ServicesViewModel(repo) as T
            }
        }

        setContent {
            val vm: ServicesViewModel = viewModel(factory = factory)
            AppNav(vm = vm)
        }
    }
}