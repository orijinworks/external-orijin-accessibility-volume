package orijin.works.accessibilityvolume

import VolumeControlScreen
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import orijin.works.accessibilityvolume.ui.theme.AccessibilityVolumeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: VolumeViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkAndRestartAccessibilityService()

        setContent {
            AccessibilityVolumeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    VolumeControlScreen(viewModel)
                }
            }
        }
    }

    private fun checkAndRestartAccessibilityService() {
        if (!isAccessibilityServiceEnabled()) {
            Log.w("MainActivity", "Accessibility Service is OFF, prompting user...")
            openAccessibilitySettings()
        } else {
            Log.d("MainActivity", "Accessibility Service is ON, restarting...")
            restartAccessibilityService()
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val serviceName = ComponentName(this, VolumeAccessibilityService::class.java).flattenToString()
        return enabledServices.contains(serviceName)
    }

    private fun restartAccessibilityService() {
        Log.d("MainActivity", "Stopping and restarting Accessibility Service...")
        stopService(Intent(this, VolumeAccessibilityService::class.java))
        startService(Intent(this, VolumeAccessibilityService::class.java))
    }

    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }
}
