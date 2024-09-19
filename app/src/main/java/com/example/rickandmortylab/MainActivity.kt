package com.example.rickandmortylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortylab.ui.theme.RickAndMortyLabTheme
import com.example.rickandmortylab.login.addLogin
import com.example.rickandmortylab.characters.addCharacters
import com.example.rickandmortylab.characterdetails.addCharacterDetails

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyLabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        addLogin(navController)
        addCharacters(navController)
        addCharacterDetails(navController)
    }
}

