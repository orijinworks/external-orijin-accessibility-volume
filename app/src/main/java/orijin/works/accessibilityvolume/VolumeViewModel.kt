package orijin.works.accessibilityvolume

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VolumeViewModel : ViewModel() {

    private val _volume = MutableStateFlow(0)
    val volume = _volume.asStateFlow()

    private var serviceAvailable = false
    private var volumeChangeJob: Job? = null

    init {
        Log.d("VolumeViewModel", "Checking Accessibility Service status...")

        VolumeAccessibilityService.setServiceListener { service ->
            viewModelScope.launch {
                delay(1000)
                serviceAvailable = true
                val currentVolume = service.getGlobalAccessibilityVolume()
                _volume.value = currentVolume
                Log.d("VolumeViewModel", "Service connected, volume: $currentVolume")
            }
        }
    }

    fun setVolume(newVolume: Int) {
        _volume.value = newVolume

        volumeChangeJob?.cancel()

        volumeChangeJob = viewModelScope.launch {
            delay(500)
            if (!serviceAvailable) {
                Log.e("VolumeViewModel", "Service not available! Retrying...")
                return@launch
            }

            val service = VolumeAccessibilityService.getInstance()
            service?.setAccessibilityVolume(newVolume)
                ?: Log.e("VolumeViewModel", "Service is still unavailable!")
        }
    }
}
