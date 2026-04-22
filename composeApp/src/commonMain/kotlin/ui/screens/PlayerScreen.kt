package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.frida.music.domain.AudioPlayer
import ui.theme.GradientEnd
import ui.theme.GradientStart
import ui.theme.SurfaceHigh

@Composable
fun PlayerScreen(audioPlayer: AudioPlayer?) {
    val currentTrack by audioPlayer?.currentTrack?.collectAsState(null) ?: remember { mutableStateOf(null) }
    val isPlaying by audioPlayer?.isPlaying?.collectAsState(false) ?: remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            // Album Art
            coil3.compose.AsyncImage(
                model = currentTrack?.coverArtUri,
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.DarkGray),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = currentTrack?.title ?: "Selecciona una canción",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currentTrack?.artist ?: "Toca una canción en Home",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
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
                Text("⏮", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                
                Surface(
                    shape = CircleShape,
                    color = GradientStart,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            if (isPlaying) audioPlayer?.pause() else audioPlayer?.resume()
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(if (isPlaying) "⏸" else "▶", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                    }
                }

                Text("⏭", color = Color.White, style = MaterialTheme.typography.headlineMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Cloud Sync Placeholder
            Button(
                onClick = { /* Sync Cloud */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceHigh.copy(alpha = 0.5f))
            ) {
                Text("Backup to Cloud (Firebase)", color = GradientEnd)
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
