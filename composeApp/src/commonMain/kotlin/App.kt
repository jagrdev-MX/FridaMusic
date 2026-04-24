package com.frida.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioScanner
import com.frida.music.domain.AudioTrack
import ui.screens.*
import ui.theme.FridaMusicTheme

@Composable
fun App(audioScanner: AudioScanner? = null, audioPlayer: AudioPlayer? = null) {
    FridaMusicTheme {
        var currentScreen by remember { mutableStateOf("home") }
        val currentTrack by audioPlayer?.currentTrack?.collectAsState() ?: remember { mutableStateOf<AudioTrack?>(null) }
        val isPlaying by audioPlayer?.isPlaying?.collectAsState() ?: remember { mutableStateOf(false) }
        val colors = MaterialTheme.colorScheme

        Scaffold(
            containerColor = colors.background,
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (currentScreen) {
                    "home" -> HomeScreen(audioScanner, audioPlayer)
                    "player" -> PlayerScreen(audioPlayer)
                    "library" -> LibraryScreen()
                    "mixer" -> MixerScreen()
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    if (currentTrack != null && currentScreen != "player") {
                        MiniPlayer(
                            track = currentTrack!!,
                            isPlaying = isPlaying,
                            onPlayPause = {
                                if (isPlaying) audioPlayer?.pause() else audioPlayer?.resume()
                            },
                            onClick = { currentScreen = "player" },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    // ── FLOATING NAV BAR ──
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                            .fillMaxWidth()
                            .height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        color = colors.surface,
                        shadowElevation = 8.dp,
                        border = androidx.compose.foundation.BorderStroke(
                            0.5.dp, colors.outline.copy(alpha = 0.3f)
                        ),
                        tonalElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NavItem(Icons.Rounded.Home, "Home", currentScreen == "home") { currentScreen = "home" }
                            NavItem(Icons.Rounded.PlayArrow, "Player", currentScreen == "player") { currentScreen = "player" }
                            NavItem(Icons.Rounded.Menu, "Library", currentScreen == "library") { currentScreen = "library" }
                            NavItem(Icons.Rounded.Settings, "Mixer", currentScreen == "mixer") { currentScreen = "mixer" }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) colors.primary else colors.onSurfaceVariant,
            modifier = Modifier.size(if (isSelected) 28.dp else 24.dp)
        )
    }
}

@Composable
fun MiniPlayer(
    track: AudioTrack,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = colors.surfaceVariant,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.outline.copy(alpha = 0.3f))
            ) {
                coil3.compose.AsyncImage(
                    model = track.coverArtUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    color = colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    color = colors.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onPlayPause) {
                Icon(
                    imageVector = if (isPlaying) Icons.Rounded.Close else Icons.Rounded.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = colors.onSurface,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
