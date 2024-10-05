package com.example.rickandmortylab.characterdetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.rickandmortylab.characters.CharacterListScreen

fun NavGraphBuilder.addCharactersGraph(navController: NavHostController) {
    navigation(
        startDestination = "character_list",
        route = "characters" 
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



