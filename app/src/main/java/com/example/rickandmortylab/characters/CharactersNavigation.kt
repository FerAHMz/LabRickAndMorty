package com.example.rickandmortylab.characters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.addCharacters(navController: NavHostController) {
    composable("characters") {
        CharacterListScreen(navController = navController)
    }
}
