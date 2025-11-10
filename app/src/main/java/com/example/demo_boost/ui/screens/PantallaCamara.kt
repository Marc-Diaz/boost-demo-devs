package com.example.demo_boost.ui.screens

import android.net.Uri
import android.opengl.GLSurfaceView
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.xr.arcore.HitResult
import androidx.xr.arcore.Plane
import com.example.demo_boost.viewmodels.ARViewModel


@Composable
fun PantallaCamara(){
    val context = LocalContext.current
    val arViewModel: ARViewModel = viewModel<ARViewModel>()


}

