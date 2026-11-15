//  Maki Dice (Android)
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import xyz.andrewmichaelpowell.makidice.ui.theme.Orange

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PageChooser(
    selectedIndex: Int,
    items: List<Pair<String, @Composable () -> Unit>>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemWidth = 80.dp
    val itemHeight = 48.dp
    val indicatorOffset by animateDpAsState(itemWidth * selectedIndex, label = "indicator")
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 6.dp,
        tonalElevation = 3.dp,
        modifier = modifier,
    ) {
        Box(modifier = Modifier.padding(4.dp)) {
            Box(
                Modifier
                    .offset(x = indicatorOffset)
                    .size(itemWidth, itemHeight)
                    .clip(CircleShape)
                    .background(Orange),
            )
            Row {
                items.forEachIndexed { index, (label, icon) ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .size(itemWidth, itemHeight)
                            .clip(CircleShape)
                            .semantics {
                                contentDescription = label
                                selected = isSelected
                            }
                            .clickable(role = Role.Tab) { onSelect(index) },
                        contentAlignment = Alignment.Center,
                    ) {
                        icon()
                    }
                }
            }
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SegmentChooser(
    selectedIndex: Int,
    labels: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = CircleShape,
        color = frostedColor(),
        border = frostedBorder(),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        BoxWithConstraints(modifier = Modifier.padding(4.dp)) {
            val itemWidth = maxWidth / labels.size
            val indicatorOffset by animateDpAsState(itemWidth * selectedIndex, label = "segment")
            Box(
                Modifier
                    .offset(x = indicatorOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(Orange),
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                labels.forEachIndexed { index, label ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .semantics { selected = isSelected }
                            .clickable(role = Role.Tab) { onSelect(index) }
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        ShrinkToFitText(
                            text = label,
                            maxFontSizeSp = MaterialTheme.typography.titleLarge.fontSize.value,
                            color = when {
                                isSelected -> androidx.compose.ui.graphics.Color.White
                                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            },
                        )
                    }
                }
            }
        }
    }
}
