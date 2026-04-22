package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.theme.GradientStart

@Composable
fun LibraryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Tu Biblioteca",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            items(10) {
                LibraryItem("Playlist #${it + 1}", "Local Storage")
            }
            
            // Espacio extra para que el MiniPlayer y la Nav Bar no tapen el contenido
            item {
                Spacer(modifier = Modifier.height(180.dp))
            }
        }

        // Botón flotante de Sync (ahora realmente flota sobre la Nav Bar)
        ExtendedFloatingActionButton(
            onClick = { /* Cloud Sync */ },
            containerColor = GradientStart,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 24.dp), // Lo subimos para que no choque con la nav bar
            icon = { Icon(Icons.Rounded.Refresh, contentDescription = "Sync") },
            text = { Text("Sync with Cloud") }
        )
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
