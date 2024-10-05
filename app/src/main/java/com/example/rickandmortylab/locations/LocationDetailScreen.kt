package com.example.rickandmortylab.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rickandmortylab.ErrorScreen
import com.example.rickandmortylab.LoadingScreen
import com.example.rickandmortylab.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    navController: NavController,
    locationId: Int,
    viewModel: LocationDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(locationId) {
        viewModel.loadLocation(locationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingScreen(onClick = { viewModel.setErrorState() })
            }
            uiState.hasError -> {
                ErrorScreen(onRetry = { viewModel.retryLoad(locationId) })
            }
            uiState.location != null -> {
                LocationDetailContent(location = uiState.location!!, paddingValues = paddingValues)
            }
        }
    }
}

@Composable
fun LocationDetailContent(location: Location, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            DetailRow(label = "ID:", value = location.id.toString())
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow(label = "Type:", value = location.type)
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow(label = "Dimension:", value = location.dimension)
        }
    }
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
