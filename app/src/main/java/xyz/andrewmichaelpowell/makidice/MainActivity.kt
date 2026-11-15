//  Maki Dice (Android)
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import xyz.andrewmichaelpowell.makidice.ui.theme.MakiDiceTheme
import xyz.andrewmichaelpowell.makidice.ui.theme.SecondarySystemBackgroundDark
import xyz.andrewmichaelpowell.makidice.ui.theme.SecondarySystemBackgroundLight

private const val TABLET_MIN_WIDTH_DP = 600
private const val SELECTED_TAB_KEY = "SelectedTab"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = SecondarySystemBackgroundLight.toArgb(),
                darkScrim = SecondarySystemBackgroundDark.toArgb(),
            )
        )
        window.isNavigationBarContrastEnforced = false

        val isTablet = resources.configuration.smallestScreenWidthDp >= TABLET_MIN_WIDTH_DP
        requestedOrientation = if (isTablet) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val preferences = getSharedPreferences("settings", MODE_PRIVATE)

        setContent {
            MakiDiceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var selectedTab by remember {
                        mutableIntStateOf(preferences.getInt(SELECTED_TAB_KEY, 0))
                    }
                    val pages = rememberSaveableStateHolder()
                    Column(modifier = Modifier.safeDrawingPadding()) {
                        Box(modifier = Modifier.weight(1f)) {
                            pages.SaveableStateProvider(selectedTab) {
                                if (selectedTab == 0) MainView() else D10View()
                            }
                        }
                        PageChooser(
                            selectedIndex = selectedTab,
                            items = listOf(
                                stringResource(R.string.calculator) to {
                                    Icon(
                                        painterResource(R.drawable.ic_dice_tab),
                                        contentDescription = null,
                                    )
                                },
                                stringResource(R.string.d10) to {
                                    Text(
                                        stringResource(R.string.d10),
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                },
                            ),
                            onSelect = { index ->
                                selectedTab = index
                                preferences.edit { putInt(SELECTED_TAB_KEY, index) }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp, bottom = 12.dp),
                        )
                    }
                }
            }
        }
    }
}
