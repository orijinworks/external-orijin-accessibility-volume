import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import orijin.works.accessibilityvolume.VolumeViewModel

@Composable
fun VolumeControlScreen(viewModel: VolumeViewModel) {
    val volumeState = viewModel.volume.collectAsState()
    val volume = volumeState.value
    val maxVolume = 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Current Accessibility Volume: $volume", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = volume.toFloat(),
            onValueChange = { newVolume ->
                val intVolume = newVolume.toInt()
                if (intVolume != volume) {
                    viewModel.setVolume(intVolume)
                    Log.d("VolumeControlScreen", "Slider adjusted volume to: $intVolume")
                }
            },
            valueRange = 0f..maxVolume.toFloat(),
            steps = maxVolume - 1,
            enabled = volume != 0
        )
    }
}




