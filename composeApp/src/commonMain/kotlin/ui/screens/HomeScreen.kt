package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioScanner
import com.frida.music.domain.AudioTrack
import ui.theme.GradientEnd
import ui.theme.GradientStart
import ui.theme.SurfaceHigh

@Composable
fun HomeScreen(audioScanner: AudioScanner?, audioPlayer: AudioPlayer?) {
    var tracks by remember { mutableStateOf<List<AudioTrack>>(emptyList()) }

    LaunchedEffect(audioScanner) {
        if (audioScanner != null) {
            tracks = audioScanner.scanLocalAudio()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "FridaMusic",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            HeroSection()
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text("Your Local Collection", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            
            if (tracks.isEmpty()) {
                Text("Buscando tu música local...", color = Color.Gray)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(tracks) { track ->
                        MusicCard(
                            title = track.title, 
                            subtitle = track.artist,
                            onClick = { audioPlayer?.play(track) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HeroSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                )
                .padding(24.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text("Unlock Premium", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Text("Remove Ads forever or watch a video to get 2 hours Premium", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun MusicCard(title: String, subtitle: String, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.width(140.dp).clickable { onClick() }) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(SurfaceHigh, RoundedCornerShape(24.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title, 
            fontWeight = FontWeight.Bold, 
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subtitle, 
            fontSize = 12.sp, 
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
