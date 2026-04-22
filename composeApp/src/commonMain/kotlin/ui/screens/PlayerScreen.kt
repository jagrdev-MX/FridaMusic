package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.GradientEnd
import ui.theme.GradientStart
import ui.theme.SurfaceHigh

@Composable
fun PlayerScreen() {
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
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Local Track",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⏮", color = Color.White, modifier = Modifier.padding(16.dp))
                
                Surface(
                    shape = CircleShape,
                    color = GradientStart,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("▶", color = Color.White)
                    }
                }

                Text("⏭", color = Color.White, modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Lyrics button
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
