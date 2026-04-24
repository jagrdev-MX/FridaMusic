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

@Composable
fun LibraryScreen() {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
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
                    style = MaterialTheme.typography.headlineLarge,
                    color = colors.onBackground
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(10) {
                LibraryItem("Playlist #${it + 1}", "Local Storage")
            }

            item {
                Spacer(modifier = Modifier.height(180.dp))
            }
        }

        ExtendedFloatingActionButton(
            onClick = { },
            containerColor = colors.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 24.dp),
            icon = { Icon(Icons.Rounded.Refresh, contentDescription = "Sync") },
            text = { Text("Sync with Cloud") }
        )
    }
}

@Composable
fun LibraryItem(name: String, subtitle: String) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(60.dp).background(colors.surfaceVariant))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, color = colors.onBackground)
            Text(text = subtitle, color = colors.onSurfaceVariant)
        }
    }
}
