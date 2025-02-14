package orijin.works.accessibilityvolume

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class VolumeAccessibilityService : AccessibilityService() {

    companion object {
        private var instance: VolumeAccessibilityService? = null
        private var listener: ((VolumeAccessibilityService) -> Unit)? = null

        fun getInstance(): VolumeAccessibilityService? {
            return instance
        }

        fun setServiceListener(callback: (VolumeAccessibilityService) -> Unit) {
            listener = callback
            instance?.let { callback(it) }
        }
    }

    private var audioManager: AudioManager? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("VolumeAccessibilityService", "Service connected.")

        instance = this
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        listener?.invoke(this)

        if (audioManager == null) {
            Log.e("VolumeAccessibilityService", "AudioManager failed to initialize!")
            return
        }

        val currentVolume = getGlobalAccessibilityVolume()
        if (currentVolume == 0) {
            setAccessibilityVolume(10)
            Log.d("VolumeAccessibilityService", "Set initial Accessibility Volume to 10.")
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {
        Log.w("VolumeAccessibilityService", "Service interrupted!")
    }

    fun getGlobalAccessibilityVolume(): Int {
        return audioManager?.getStreamVolume(AudioManager.STREAM_ACCESSIBILITY) ?: 0
    }

    fun setAccessibilityVolume(volume: Int) {
        val manager = audioManager
        if (manager == null) {
            Log.e("VolumeAccessibilityService", "AudioManager not initialized!")
            return
        }

        val maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_ACCESSIBILITY)
        val safeVolume = volume.coerceIn(0, maxVolume)

        manager.setStreamVolume(AudioManager.STREAM_ACCESSIBILITY, safeVolume, 0)
        Log.d("VolumeAccessibilityService", "Set Accessibility Volume to: $safeVolume")
    }
}
