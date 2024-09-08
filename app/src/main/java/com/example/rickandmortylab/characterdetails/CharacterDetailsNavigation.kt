package com.example.rickandmortylab.characterdetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.addCharacterDetails(navController: NavHostController) {
    composable("character_details/{characterId}") { backStackEntry ->
        val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
        CharacterDetailScreen(navController = navController, characterId = characterId)
    }
}

