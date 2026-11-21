package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PanelConfig
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.NeonCyan
import kotlin.math.roundToInt

@Composable
fun FloatingControlWidget(
    config: PanelConfig,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onUpdateConfig: (PanelConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableStateOf(50f) }
    var offsetY by remember { mutableStateOf(150f) }

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    listOf(DarkCard.copy(alpha = 0.95f), Color(0xFF1B080C).copy(alpha = 0.95f))
                ),
                RoundedCornerShape(16.dp)
            )
            .border(1.5.dp, CrimsonGlow.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.width(240.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(CrimsonGlow)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "FIRE HUD V2",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand/Collapse",
                    tint = NeonCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Spacer(modifier = Modifier.height(2.dp))
                    FloatingToggleRow(
                        title = "ESP Box",
                        checked = config.espBox,
                        onCheckedChange = { onUpdateConfig(config.copy(espBox = it)) }
                    )
                    FloatingToggleRow(
                        title = "Head Lock",
                        checked = config.headLock,
                        onCheckedChange = { onUpdateConfig(config.copy(headLock = it)) }
                    )
                    FloatingToggleRow(
                        title = "Backward Alert",
                        checked = config.backwardAlarm,
                        onCheckedChange = { onUpdateConfig(config.copy(backwardAlarm = it)) }
                    )
                    FloatingToggleRow(
                        title = "Gloo Wall Insta",
                        checked = config.glooWallSpeed,
                        onCheckedChange = { onUpdateConfig(config.copy(glooWallSpeed = it)) }
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingToggleRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
            fontSize = 12.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = CrimsonGlow,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = DarkCardBorder
            ),
            modifier = Modifier.size(36.dp, 24.dp)
        )
    }
}
