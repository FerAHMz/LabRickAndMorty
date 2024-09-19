package com.example.rickandmortylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortylab.characters.addCharactersGraph
import com.example.rickandmortylab.login.addLogin
import com.example.rickandmortylab.locations.addLocations
import com.example.rickandmortylab.profiel.addProfile
import com.example.rickandmortylab.ui.theme.RickAndMortyLabTheme

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

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = currentBackStackEntry?.destination
    val showBottomBar = currentDestination?.route != "login"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        MainNavHost(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        addLogin(navController)
        addLocations(navController)
        addProfile(navController)
        addCharactersGraph(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val items = listOf("characters", "locations", "profile")
    val labels = listOf("Characters", "Locations", "Profile")

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(labels[index]) },
                icon = {
                    Icon(
                        imageVector = when (item) {
                            "characters" -> Icons.Default.Person
                            "locations" -> Icons.Default.LocationOn
                            else -> Icons.Default.AccountCircle
                        },
                        contentDescription = null
                    )
                },
                selected = currentDestination == item,
                onClick = {
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
