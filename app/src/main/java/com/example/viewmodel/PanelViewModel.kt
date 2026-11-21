package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AiState
import com.example.model.PanelConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PanelViewModel : ViewModel() {
    private val _config = MutableStateFlow(PanelConfig())
    val config: StateFlow<PanelConfig> = _config.asStateFlow()

    private val _isFloatingVisible = MutableStateFlow(true)
    val isFloatingVisible: StateFlow<Boolean> = _isFloatingVisible.asStateFlow()

    private val _isFloatingExpanded = MutableStateFlow(false)
    val isFloatingExpanded: StateFlow<Boolean> = _isFloatingExpanded.asStateFlow()

    private val _aiState = MutableStateFlow<AiState>(AiState.Idle)
    val aiState: StateFlow<AiState> = _aiState.asStateFlow()

    private val _aimScore = MutableStateFlow(0)
    val aimScore: StateFlow<Int> = _aimScore.asStateFlow()

    fun updateConfig(updater: (PanelConfig) -> PanelConfig) {
        _config.value = updater(_config.value)
    }

    fun toggleFloatingVisibility() {
        _isFloatingVisible.value = !_isFloatingVisible.value
    }

    fun toggleFloatingExpanded() {
        _isFloatingExpanded.value = !_isFloatingExpanded.value
    }

    fun recordHit() {
        _aimScore.value += 10
    }

    fun resetAimScore() {
        _aimScore.value = 0
    }

    fun analyzeHeadshotCalibration(weaponName: String, playerSens: Int) {
        viewModelScope.launch {
            _aiState.value = AiState.Loading
            // Simulated deep tactical AI headshot calibration response (Offline fallback since Firebase was declined)
            delay(1200)
            val accuracy = 88 + (Math.random() * 11).toInt()
            val dragForce = 60 + (playerSens % 30)
            val responseText = """
                [AI HEADSHOT TACTICAL COACH - OFFLINE SIMULATION]
                Weapon Target: $weaponName | Sensitivity: $playerSens
                
                • Crosshair Placement: Keep crosshair at head height (approx 15° upward tilt) before engagement.
                • Drag Speed: Optimal upward drag force is $dragForce% with smooth exponential curve.
                • Recoil Control: Fire in 3-bullet bursts for SMGs / ARs to maintain pinpoint crosshair lock on target's helmet hitbox.
                • Positioning: Utilize cover before initiating gloo wall quick-deploy sequence.
                
                Note: Cloud AI features require Firebase. Connect Firebase AI Logic for dynamic LLM generation.
            """.trimIndent()

            val metrics = mapOf(
                "Accuracy Score" to accuracy,
                "Drag Speed Index" to dragForce,
                "Headshot Ratio" to 92,
                "Reaction Time (ms)" to 175
            )
            _aiState.value = AiState.Success(responseText, metrics)
        }
    }
}
