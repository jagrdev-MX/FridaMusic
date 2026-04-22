package ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
        modifier = Modifier.fillMaxSize()
    ) {
        // Dynamic Blurred Background
        if (currentTrack?.coverArtUri != null) {
            coil3.compose.AsyncImage(
                model = currentTrack?.coverArtUri,
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(70.dp), // Strong glassmorphism blur
                contentScale = ContentScale.Crop
            )
            // Overlay to make text readable and blend with controls
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )
        } else {
            // Fallback gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF16213E))))
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            // Static Album Art
            coil3.compose.AsyncImage(
                model = currentTrack?.coverArtUri,
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.DarkGray.copy(alpha = 0.5f)),
                contentScale = ContentScale.Crop
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

            // Premium Controls with Vector Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Previous */ }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Previous", tint = Color.White, modifier = Modifier.size(40.dp))
                }
                
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
                        Icon(
                            imageVector = if (isPlaying) Icons.Rounded.Close else Icons.Rounded.PlayArrow,
                            contentDescription = "Play/Pause", 
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                IconButton(onClick = { /* Next */ }) {
                    Icon(Icons.Rounded.ArrowForward, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(40.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Minimalist Cloud Sync Button
            Button(
                onClick = { /* Sync Cloud */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceHigh.copy(alpha = 0.4f))
            ) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = "Cloud", tint = GradientEnd, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Respaldar en la Nube", color = GradientEnd)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
