package com.example.rickandmortylab.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmortylab.data.LocationDb

@Composable
fun LocationsScreen(navController: NavController) {
    val locations = LocationDb().getAllLocations()

    if (locations.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No locations available.")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
                    Text(text = location.type, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
