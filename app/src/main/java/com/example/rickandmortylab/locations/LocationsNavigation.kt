package com.example.rickandmortylab.locations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmortylab.data.LocationDao
import com.example.rickandmortylab.RickAndMortyApiClient

fun NavGraphBuilder.addLocations(
    navController: NavHostController,
    locationDao: LocationDao,
    apiClient: RickAndMortyApiClient
) {
    composable("locations") {
        LocationsScreen(
            navController = navController,
            locationDao = locationDao,
            apiClient = apiClient
        )
    }

    composable(
        route = "locationDetails/{locationId}",
        arguments = listOf(navArgument("locationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val locationId = backStackEntry.arguments?.getInt("locationId")
        if (locationId != null) {
            LocationDetailScreen(
                navController = navController,
                locationId = locationId
            )
        }
    }
}
