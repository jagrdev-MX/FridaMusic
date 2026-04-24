package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MixerScreen() {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(24.dp)
    ) {
        Text(
            text = "Advanced Mixer",
            style = MaterialTheme.typography.headlineLarge,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Equalizer", color = colors.onBackground)
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
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = colors.onBackground)
        var sliderValue by remember { mutableStateOf(0.5f) }
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.width(200.dp)
        )
    }
}
