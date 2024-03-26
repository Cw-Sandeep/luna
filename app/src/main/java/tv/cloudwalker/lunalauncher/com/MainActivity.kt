package tv.cloudwalker.lunalauncher.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.tv.material3.ExperimentalTvMaterial3Api
import dagger.hilt.android.AndroidEntryPoint
import tv.cloudwalker.lunalauncher.com.screens.MainScreen
import tv.cloudwalker.lunalauncher.com.ui.theme.LunalauncherTheme
import tv.cloudwalker.lunalauncher.com.utils.SkinResource

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunalauncherTheme {
                val skinResource = SkinResource(this)
                    MainScreen(
                        skinResource,applicationContext,OnBackPressedDispatcher::onBackPressed)

            }
        }
    }
}
