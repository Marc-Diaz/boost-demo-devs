package com.example.demo_boost.ui.screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.ar.core.CameraConfig
import com.google.ar.core.Config
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.rememberARCameraStream
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import com.google.ar.core.CameraConfigFilter


@Composable
fun PantallaAR() {

    // Filament 3D Engine
    val engine = rememberEngine()

    // Asset loaders

    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    ARScene(
        modifier = Modifier.fillMaxSize().background(Color.Red),

        // Configure AR session features
        sessionFeatures = setOf(),

        // Configure AR session settings

        sessionConfiguration = { session, config ->
            // Enable depth if supported on the device
            config.depthMode =
                when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
            config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },

        // Enable plane detection visualization
        planeRenderer = true,

        // Configure camera stream
        cameraStream = rememberARCameraStream(materialLoader),

        // Session lifecycle callbacks
        onSessionCreated = { session ->
            // Handle session creation
        },
        onSessionResumed = { session ->
            // Handle session resume
        },
        onSessionPaused = { session ->
            // Handle session pause
        },

        // Frame update callback
        onSessionUpdated = { session, updatedFrame ->
            // Process AR frame updates
        },

        // Error handling
        onSessionFailed = { exception ->
            // Handle ARCore session errors
        },

        // Track camera tracking state changes
        onTrackingFailureChanged = { trackingFailureReason ->
            // Handle tracking failures
        },
        childNodes = rememberNodes{
            add(
                ModelNode(
                    // Create a single instance model from assets file
                    modelInstance = modelLoader.createModelInstance(
                        assetFileLocation = "models/prueba_cloth_Jordi.glb"
                    ),
                    // Make the model fit into a 1 unit cube
                    scaleToUnits = 1.0f
                )
            )
        }
    )
}
