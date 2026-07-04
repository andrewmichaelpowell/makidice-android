package xyz.andrewmichaelpowell.makidice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.andrewmichaelpowell.makidice.ui.theme.MakiDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MakiDiceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.safeDrawingPadding(),
                    ) {
                        composable("main") {
                            MainView(onOpenD10 = { navController.navigate("d10") })
                        }
                        composable("d10") {
                            D10View(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
