package com.example.model

data class PanelConfig(
    val espBox: Boolean = true,
    val espLine: Boolean = false,
    val espDistance: Boolean = true,
    val headLock: Boolean = true,
    val headSmoothness: Float = 65f,
    val backwardAlarm: Boolean = true,
    val backwardSens: Float = 80f,
    val glooWallSpeed: Boolean = true,
    val glooWallInsta: Boolean = false,
    val freestyleDpi: Int = 540,
    val freestyleAimAssist: Float = 75f,
    val accentColorIndex: Int = 0 // 0: Crimson, 1: Neon Cyan, 2: Gold, 3: Purple
)

data class AiHeadshotTip(
    val weapon: String,
    val crosshairPlacement: String,
    val dragSpeed: String,
    val tip: String
)

sealed interface AiState {
    data object Idle : AiState
    data object Loading : AiState
    data class Success(val responseText: String, val metrics: Map<String, Int>) : AiState
    data class Error(val message: String) : AiState
}
