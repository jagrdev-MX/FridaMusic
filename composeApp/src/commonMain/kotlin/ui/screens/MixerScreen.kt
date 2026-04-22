package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.SurfaceHigh

@Composable
fun MixerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Text(
            text = "Advanced Mixer",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("Equalizer", color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(7) {
                var sliderValue by remember { mutableStateOf(0.5f) }
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    modifier = Modifier.width(40.dp).fillMaxHeight()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        ControlKnob("Bass Boost")
        Spacer(modifier = Modifier.height(16.dp))
        ControlKnob("3D Surround")
    }
}

@Composable
fun ControlKnob(label: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White)
        var sliderValue by remember { mutableStateOf(0.5f) }
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.width(200.dp)
        )
    }
}
