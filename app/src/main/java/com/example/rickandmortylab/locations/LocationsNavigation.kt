package com.example.rickandmortylab.locations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.addLocations(navController: NavHostController) {
    composable("locations") {
        LocationsScreen(navController = navController)
    }

    composable("locationDetails/{locationId}") { backStackEntry ->
        val locationId = backStackEntry.arguments?.getString("locationId")?.toIntOrNull()
        if (locationId != null) {
            LocationDetailScreen(navController = navController, locationId = locationId)
        }
    }
}
