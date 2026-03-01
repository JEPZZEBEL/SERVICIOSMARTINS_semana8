package com.example.serviciosmartins.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav(vm: ServicesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "services") {

        composable("services") {
            ServicesScreen(
                vm = vm,
                onOpenDetail = { id -> navController.navigate("detail/$id") }
            )
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
            DetailScreen(
                id = id,
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}