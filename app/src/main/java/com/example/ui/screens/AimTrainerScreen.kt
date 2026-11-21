package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AimTrainerScreen(
    aimScore: Int,
    onRecordHit: () -> Unit,
    onResetScore: () -> Unit,
    modifier: Modifier = Modifier
) {
    var targetX by remember { mutableStateOf(150f) }
    var targetY by remember { mutableStateOf(300f) }
    var timeLeft by remember { mutableStateOf(30) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            timeLeft = 30
            while (timeLeft > 0 && isRunning) {
                delay(1000)
                timeLeft--
            }
            isRunning = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkCard)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardBorder.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "AIM & HEADSHOT SIMULATOR",
                        style = MaterialTheme.typography.titleLarge,
                        color = NeonCyan,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Tap targets as fast as possible for headshot calibration reflex training.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = { onResetScore(); isRunning = false }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = CrimsonGlow)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Score & Timer Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2030)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Gamepad, contentDescription = null, tint = NeonGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "SCORE: $aimScore", color = NeonGold, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2030)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = CrimsonGlow)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "TIME: ${timeLeft}s", color = CrimsonGlow, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Arena Canvas Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF070A12), Color(0xFF120508))
                    )
                )
                .border(2.dp, CrimsonGlow.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
        ) {
            // Background grid lines
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 60f
                var x = 0f
                while (x < size.width) {
                    drawLine(Color(0xFF1E293B), Offset(x, 0f), Offset(x, size.height), 1f)
                    x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(Color(0xFF1E293B), Offset(0f, y), Offset(size.width, y), 1f)
                    y += step
                }
            }

            if (!isRunning && timeLeft == 0) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "SESSION COMPLETED!", color = NeonGold, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Final Headshot Score: $aimScore", color = Color.White, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { isRunning = true; onResetScore() },
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonGlow)
                    ) {
                        Text("START NEW DRILL", color = Color.White)
                    }
                }
            } else if (!isRunning) {
                Button(
                    onClick = { isRunning = true; onResetScore() },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonGlow)
                ) {
                    Text("START DRILL", color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            } else {
                // Interactive target
                Box(
                    modifier = Modifier
                        .offset(targetX.dp, targetY.dp)
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(CrimsonGlow.copy(alpha = 0.8f))
                        .border(3.dp, Color.White, CircleShape)
                        .clickable {
                            onRecordHit()
                            targetX = Random.nextFloat() * 250f + 20f
                            targetY = Random.nextFloat() * 350f + 40f
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(NeonGold)
                    )
                }
            }
        }
    }
}
