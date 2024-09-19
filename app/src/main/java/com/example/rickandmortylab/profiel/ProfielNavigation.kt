package com.example.rickandmortylab.profiel

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.rickandmortylab.profile.ProfileScreen

fun NavGraphBuilder.addProfile(navController: NavHostController) {
    composable("profile") {
        ProfileScreen(navController = navController)
    }
}
