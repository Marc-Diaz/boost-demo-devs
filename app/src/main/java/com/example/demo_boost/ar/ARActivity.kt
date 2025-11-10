package com.example.demo_boost.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.demo_boost.ui.screens.PantallaAR
import com.google.ar.core.Config
import com.google.ar.core.Session

class ARActivity : ComponentActivity() {
    private lateinit var arSession: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la sesión de ARCore
        arSession = Session(this)
        val config = Config(arSession).apply {
            planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
        }
        arSession.configure(config)

        setContent {
            PantallaAR(arSession)
        }
    }
}