package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.frida.music.domain.AudioPlayer

@Composable
fun PlayerScreen(audioPlayer: AudioPlayer?) {
    val currentTrack by audioPlayer?.currentTrack?.collectAsState(null) ?: remember { mutableStateOf(null) }
    val isPlaying by audioPlayer?.isPlaying?.collectAsState(false) ?: remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Album Art — solid background, no blur
            coil3.compose.AsyncImage(
                model = currentTrack?.coverArtUri,
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(colors.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = currentTrack?.title ?: "Selecciona una canción",
                style = MaterialTheme.typography.headlineMedium,
                color = colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currentTrack?.artist ?: "Toca una canción en Home",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Previous */ }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Previous", tint = colors.onBackground, modifier = Modifier.size(40.dp))
                }

                Surface(
                    shape = CircleShape,
                    color = colors.primary,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            if (isPlaying) audioPlayer?.pause() else audioPlayer?.resume()
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Rounded.Close else Icons.Rounded.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                IconButton(onClick = { /* Next */ }) {
                    Icon(Icons.Rounded.ArrowForward, contentDescription = "Next", tint = colors.onBackground, modifier = Modifier.size(40.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Cloud Sync Button
            Button(
                onClick = { /* Sync Cloud */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.surfaceVariant)
            ) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = "Cloud", tint = colors.primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Respaldar en la Nube", color = colors.primary)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
