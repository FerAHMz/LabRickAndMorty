package com.example.rickandmortylab

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortylab.characters.addCharactersGraph
import com.example.rickandmortylab.login.addLogin
import com.example.rickandmortylab.locations.addLocations
import com.example.rickandmortylab.main.MainViewModel
import com.example.rickandmortylab.ui.theme.RickAndMortyLabTheme
import com.example.rickandmortylab.main.MainViewModelFactory
import com.example.rickandmortylab.profile.addProfile
import com.example.rickandmortylab.data.AppDatabase
import com.example.rickandmortylab.data.CharacterDao

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        setContent {
            RickAndMortyLabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as Application))) {
    val navController = rememberNavController()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()

    val context = LocalContext.current.applicationContext
    val characterDao = remember { AppDatabase.getDatabase(context).characterDao() }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn && navController.currentDestination?.route != "characters") {
            navController.navigate("characters") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = currentBackStackEntry?.destination
    val showBottomBar = !setOf("login").contains(currentDestination?.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        MainNavHost(
            navController = navController,
            characterDao = characterDao,
            modifier = Modifier.padding(paddingValues)
        )
    }
}




@Composable
fun MainNavHost(
    navController: NavHostController,
    characterDao: CharacterDao,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        addLogin(navController)
        addLocations(navController)
        addProfile(navController)
        addCharactersGraph(navController, characterDao)
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
