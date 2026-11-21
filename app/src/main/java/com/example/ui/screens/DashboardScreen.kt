package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiState
import com.example.model.PanelConfig
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.viewmodel.PanelViewModel

@Composable
fun DashboardScreen(
    viewModel: PanelViewModel
) {
    val config by viewModel.config.collectAsState()
    val aiState by viewModel.aiState.collectAsState()
    val aimScore by viewModel.aimScore.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkCard,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Tune, contentDescription = "Panel") },
                    label = { Text("HUD Panel") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CrimsonGlow,
                        selectedTextColor = CrimsonGlow,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = DarkCardBorder
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "AI Coach") },
                    label = { Text("AI Headshot") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CrimsonGlow,
                        selectedTextColor = CrimsonGlow,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = DarkCardBorder
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Gamepad, contentDescription = "Aim Trainer") },
                    label = { Text("Aim Trainer") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CrimsonGlow,
                        selectedTextColor = CrimsonGlow,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = DarkCardBorder
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF070A12), Color(0xFF14070A))
                    )
                )
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HudPanelTab(config = config, onUpdateConfig = { viewModel.updateConfig { it } })
                1 -> AiHeadshotTab(aiState = aiState, onAnalyze = { weapon, sens -> viewModel.analyzeHeadshotCalibration(weapon, sens) })
                2 -> AimTrainerScreen(
                    aimScore = aimScore,
                    onRecordHit = { viewModel.recordHit() },
                    onResetScore = { viewModel.resetAimScore() }
                )
            }
        }
    }
}

@Composable
fun HudPanelTab(
    config: PanelConfig,
    onUpdateConfig: () -> Unit
) {
    var localConfig by remember { mutableStateOf(config) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(CrimsonGlow)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "FIRE HUD INDIA - CONTROL DASHBOARD",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Professional gaming control panel & calibration interface for aiming precision and visual performance.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }
        }

        item {
            Text(
                text = "ESP & VISUAL MODES",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PanelToggleItem(
                        title = "ESP Box Overlay",
                        subtitle = "Display tactical bounding boxes on target models",
                        checked = localConfig.espBox,
                        onCheckedChange = { localConfig = localConfig.copy(espBox = it); onUpdateConfig() }
                    )
                    PanelToggleItem(
                        title = "ESP Distance Meter",
                        subtitle = "Show distance telemetry indicators",
                        checked = localConfig.espDistance,
                        onCheckedChange = { localConfig = localConfig.copy(espDistance = it); onUpdateConfig() }
                    )
                }
            }
        }

        item {
            Text(
                text = "HEAD LOCK & SENSITIVITY CALIBRATION",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PanelToggleItem(
                        title = "Head Lock Assist",
                        subtitle = "Target alignment correction for crosshair stability",
                        checked = localConfig.headLock,
                        onCheckedChange = { localConfig = localConfig.copy(headLock = it); onUpdateConfig() }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Head Smoothness: ${localConfig.headSmoothness.toInt()}%",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                    Slider(
                        value = localConfig.headSmoothness,
                        onValueChange = { localConfig = localConfig.copy(headSmoothness = it); onUpdateConfig() },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonGlow,
                            activeTrackColor = CrimsonGlow,
                            inactiveTrackColor = DarkCardBorder
                        )
                    )
                }
            }
        }

        item {
            Text(
                text = "TACTICAL UTILITIES",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PanelToggleItem(
                        title = "Backward Threat Alarm",
                        subtitle = "Audio-visual warning when targeted from behind",
                        checked = localConfig.backwardAlarm,
                        onCheckedChange = { localConfig = localConfig.copy(backwardAlarm = it); onUpdateConfig() }
                    )
                    PanelToggleItem(
                        title = "Instant Gloo Wall Deploy",
                        subtitle = "Optimize touch response for rapid defensive barriers",
                        checked = localConfig.glooWallSpeed,
                        onCheckedChange = { localConfig = localConfig.copy(glooWallSpeed = it); onUpdateConfig() }
                    )
                }
            }
        }
    }
}

@Composable
fun PanelToggleItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = CrimsonGlow,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = DarkCardBorder
            )
        )
    }
}

@Composable
fun AiHeadshotTab(
    aiState: AiState,
    onAnalyze: (String, Int) -> Unit
) {
    var selectedWeapon by remember { mutableStateOf("MP40 / SMG") }
    var sensValue by remember { mutableIntStateOf(85) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = NeonGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI HEADSHOT COACH & CALIBRATOR",
                            style = MaterialTheme.typography.titleLarge,
                            color = NeonGold,
                            fontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Analyze your weapon recoil pattern, crosshair drag speed, and optimal sensitivity tuning with AI intelligence.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Select Weapon Class", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("MP40 / SMG", "M1887 Shotgun", "SCAR / AR").forEach { weapon ->
                            val isSelected = selectedWeapon == weapon
                            Button(
                                onClick = { selectedWeapon = weapon },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) CrimsonGlow else DarkCardBorder
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(text = weapon, fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "General Sensitivity: $sensValue", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Slider(
                        value = sensValue.toFloat(),
                        onValueChange = { sensValue = it.toInt() },
                        valueRange = 50f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = CrimsonGlow,
                            activeTrackColor = CrimsonGlow,
                            inactiveTrackColor = DarkCardBorder
                        )
                    )

                    Button(
                        onClick = { onAnalyze(selectedWeapon, sensValue) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonGlow),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("RUN AI HEADSHOT CALIBRATION", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            when (aiState) {
                is AiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = CrimsonGlow)
                    }
                }
                is AiState.Success -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCard)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "AI ANALYSIS RESULTS", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = aiState.responseText, color = Color.LightGray, fontSize = 13.sp, lineHeight = 18.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                aiState.metrics.forEach { (key, value) ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "$value%", color = NeonGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(text = key, color = Color.Gray, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }
                is AiState.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3F0A14))
                    ) {
                        Text(
                            text = aiState.message,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 13.sp
                        )
                    }
                }
                is AiState.Idle -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCard)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Color.Gray)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Tap 'Run AI Headshot Calibration' to receive custom drag and recoil recommendations.",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
