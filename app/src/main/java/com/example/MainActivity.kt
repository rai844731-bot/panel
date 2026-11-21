package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.FloatingControlWidget
import com.example.ui.screens.DashboardScreen
import com.example.ui.theme.FireHUDTheme
import com.example.viewmodel.PanelViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PanelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FireHUDTheme {
                val config by viewModel.config.collectAsState()
                val isExpanded by viewModel.isFloatingExpanded.collectAsState()
                val isVisible by viewModel.isFloatingVisible.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        DashboardScreen(viewModel = viewModel)

                        if (isVisible) {
                            FloatingControlWidget(
                                config = config,
                                isExpanded = isExpanded,
                                onToggleExpand = { viewModel.toggleFloatingExpanded() },
                                onUpdateConfig = { viewModel.updateConfig { it } }
                            )
                        }
                    }
                }
            }
        }
    }
}
