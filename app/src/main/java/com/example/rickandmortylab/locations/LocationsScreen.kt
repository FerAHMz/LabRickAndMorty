package com.example.rickandmortylab.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rickandmortylab.model.Location
import com.example.rickandmortylab.ErrorScreen
import com.example.rickandmortylab.LoadingScreen
import com.example.rickandmortylab.data.LocationDao
import com.example.rickandmortylab.RickAndMortyApiClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    navController: NavController,
    apiClient: RickAndMortyApiClient,
    locationDao: LocationDao,
    viewModel: LocationListViewModel = viewModel(factory = LocationListViewModelFactory(apiClient, locationDao))
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingScreen(onClick = { viewModel.setErrorState() })
            }
            uiState.hasError -> {
                ErrorScreen(onRetry = { viewModel.retryLoad() })
            }
            uiState.data.isNotEmpty() -> {
                LocationList(
                    navController = navController,
                    locations = uiState.data,
                    paddingValues = paddingValues
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No locations available")
                }
            }
        }
    }
}

@Composable
fun LocationList(
    navController: NavController,
    locations: List<Location>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(locations) { location ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("locationDetails/${location.id}")
                    }
            ) {
                Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                Text(text = location.type ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

