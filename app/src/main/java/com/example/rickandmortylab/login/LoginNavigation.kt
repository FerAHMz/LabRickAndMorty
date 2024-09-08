package com.example.rickandmortylab.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.addLogin(navController: NavHostController) {
    composable("login") {
        LoginScreen(navController = navController)
    }
}

