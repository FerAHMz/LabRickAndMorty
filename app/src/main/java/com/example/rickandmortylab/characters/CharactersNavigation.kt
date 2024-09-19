package com.example.rickandmortylab.characters

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.rickandmortylab.characterdetails.CharacterDetailScreen

fun NavGraphBuilder.addCharactersGraph(navController: NavHostController) {
    navigation(
        startDestination = "character_list",
        route = "characters" // Esta es la ruta del nested graph de characters
    ) {
        composable("character_list") {
            CharacterListScreen(navController = navController)
        }
        composable(
            route = "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            characterId?.let {
                CharacterDetailScreen(navController = navController, characterId = it)
            }
        }
    }
}

