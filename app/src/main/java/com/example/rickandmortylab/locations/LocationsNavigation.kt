package com.example.rickandmortylab.locations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.addLocations(navController: NavHostController) {
    composable("locations") {
        LocationsScreen(navController = navController)
    }

    composable(
        route = "locationDetails/{locationId}",
        arguments = listOf(navArgument("locationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val locationId = backStackEntry.arguments?.getInt("locationId")
        if (locationId != null) {
            LocationDetailScreen(navController = navController, locationId = locationId)
        }
    }
}

