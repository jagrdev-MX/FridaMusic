package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.GradientStart

@Composable
fun LibraryScreen() {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Cloud Sync */ },
                containerColor = GradientStart,
                contentColor = Color.White,
                icon = { Text("☁️") },
                text = { Text("Sync with Cloud") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            item {
                Text(
                    text = "Library",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            items(10) {
                LibraryItem("Playlist #${it + 1}", "Local Storage")
            }
        }
    }
}

@Composable
fun LibraryItem(name: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(60.dp).background(Color.DarkGray))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, color = Color.White)
            Text(text = subtitle, color = Color.Gray)
        }
    }
}
