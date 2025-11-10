package com.example.demo_boost.ui.screens

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demo_boost.viewmodels.ARViewModel
import com.google.ar.core.Session


@Composable
fun PantallaAR(arSession: Session) {
    Box(Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                val surfaceView = SurfaceView(context)
                val renderer = FilamentRenderer(context, session, surfaceView)
                surfaceView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay con Compose
        Column(
            Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Text("Toca para colocar objeto", color = Color.White)
        }
    }
}
