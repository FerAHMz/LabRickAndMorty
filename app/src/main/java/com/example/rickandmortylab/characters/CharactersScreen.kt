package com.example.rickandmortylab.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortylab.ErrorScreen
import com.example.rickandmortylab.LoadingScreen
import com.example.rickandmortylab.data.CharacterDao
import com.example.rickandmortylab.main.MainViewModel
import com.example.rickandmortylab.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(),
    characterDao: CharacterDao
) {
    val viewModel: CharacterListViewModel = viewModel()

    val isSyncing by mainViewModel.isSyncing
    val uiState by viewModel.uiState.collectAsState()

    if (isSyncing) {
        LoadingScreen(onClick = { /* No action needed */ })
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Characters") },
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
                    CharacterList(
                        navController = navController,
                        characters = uiState.data,
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
                        Text(text = "No characters available")
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterList(
    navController: NavController,
    characters: List<Character>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(characters) { character ->
            CharacterRow(character = character) {
                navController.navigate("character_details/${character.id}")
            }
        }
    }
}

@Composable
fun CharacterRow(character: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(model = character.image)

        if (painter.state is AsyncImagePainter.State.Error) {
            Image(
                imageVector = Icons.Filled.Error,
                contentDescription = "Error loading image",
                modifier = Modifier.size(50.dp)
            )
        } else {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = character.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${character.species} - ${character.status}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

