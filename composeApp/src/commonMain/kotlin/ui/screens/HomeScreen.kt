package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioScanner
import com.frida.music.domain.AudioTrack

@Composable
fun HomeScreen(audioScanner: AudioScanner?, audioPlayer: AudioPlayer?) {
    var tracks by remember { mutableStateOf<List<AudioTrack>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(audioScanner) {
        if (audioScanner != null) {
            tracks = audioScanner.scanLocalAudio()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FridaMusic",
                        style = MaterialTheme.typography.displaySmall,
                        color = colors.onBackground
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { }) {
                            Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = colors.onBackground)
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Rounded.AccountCircle, contentDescription = "Profile", tint = colors.onBackground)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar tu música...") },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Search", tint = colors.onSurfaceVariant) },
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.primary,
                        unfocusedBorderColor = colors.outline,
                        focusedContainerColor = colors.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = colors.surfaceVariant.copy(alpha = 0.3f),
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { CategoryChip("Todo", isSelected = true) }
                    item { CategoryChip("Favoritos", isSelected = false) }
                    item { CategoryChip("Playlists", isSelected = false) }
                    item { CategoryChip("Podcasts", isSelected = false) }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                HeroSection()
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text("Accesos Rápidos", style = MaterialTheme.typography.titleLarge, color = colors.onBackground)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { QuickActionCard(Icons.Rounded.Favorite, "Me Gusta", colors.primary) }
                    item { QuickActionCard(Icons.Rounded.PlayArrow, "Aleatorio", Color(0xFF6A1B9A), onClick = {
                        if (tracks.isNotEmpty()) audioPlayer?.play(tracks.random())
                    }) }
                    item { QuickActionCard(Icons.Rounded.Refresh, "Recientes", Color(0xFF0277BD)) }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text("Tu Colección Local", style = MaterialTheme.typography.titleLarge, color = colors.onBackground)
                Spacer(modifier = Modifier.height(16.dp))

                val filteredTracks = tracks.filter { it.title.contains(searchQuery, ignoreCase = true) || it.artist.contains(searchQuery, ignoreCase = true) }

                if (filteredTracks.isEmpty()) {
                    Text(
                        if (tracks.isEmpty()) "Buscando tu música local..." else "No se encontraron resultados",
                        color = colors.onSurfaceVariant
                    )
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(filteredTracks) { track ->
                            MusicCard(
                                title = track.title,
                                subtitle = track.artist,
                                coverUri = track.coverArtUri,
                                onClick = { audioPlayer?.play(track) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean) {
    val colors = MaterialTheme.colorScheme
    Surface(
        shape = CircleShape,
        color = if (isSelected) colors.primary else colors.surfaceVariant,
        modifier = Modifier.clickable { }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else colors.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun QuickActionCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, color: Color, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.width(140.dp).height(100.dp).clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(48.dp).align(Alignment.BottomEnd))
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.TopStart))
        }
    }
}

@Composable
fun HeroSection() {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
                .padding(24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text("Desbloquea Premium", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Sin anuncios, y máxima calidad.", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    shape = CircleShape
                ) {
                    Text("Obtener 2 horas gratis", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MusicCard(title: String, subtitle: String, coverUri: String?, onClick: () -> Unit = {}) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = Modifier.width(140.dp).clickable { onClick() }) {
        coil3.compose.AsyncImage(
            model = coverUri,
            contentDescription = "Album Art",
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.surfaceVariant),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = colors.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
