package com.example.demo_boost.ui.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.sceneview.Scene
import io.github.sceneview.node.ModelNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.CylinderNode
import io.github.sceneview.rememberCameraManipulator
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironment
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView


@Composable
fun PantallaAR() {

    // Filament 3D Engine
    val engine = rememberEngine()

    // Asset loaders
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    Scene(
        modifier = Modifier.fillMaxSize(),
        engine = engine,

        // Core rendering components
        view = rememberView(engine),
        renderer = rememberRenderer(engine),
        scene = rememberScene(engine),

        // Asset loaders
        modelLoader = modelLoader,
        materialLoader = materialLoader,
        environmentLoader = environmentLoader,
        mainLightNode = rememberMainLightNode(engine){
            intensity = 100_000f
        },

        // Configure camera position
        cameraNode = rememberCameraNode(engine) {
            position = Position(z = 4.0f)
        },

        // Enable user interaction with the camera
        cameraManipulator = rememberCameraManipulator(),

        // Add 3D models and objects to the scene
        childNodes = rememberNodes {
            // Add a glTF model
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

            // Add a 3D cylinder with custom material
            add(
                CylinderNode(
                    engine = engine,
                    radius = 0.2f,
                    height = 2.0f,
                    // Simple colored material with physics properties
                    materialInstance = materialLoader.createColorInstance(
                        color = Color.Blue,
                        metallic = 0.5f,
                        roughness = 0.2f,
                        reflectance = 0.4f
                    )
                ).apply {
                    // Define the node position and rotation
                    transform(
                        position = Position(y = 1.0f),
                        rotation = io.github.sceneview.math.Rotation(x = 90.0f)
                    )
                })
        },

        // Handle user interactions
        onGestureListener = rememberOnGestureListener(
            onDoubleTapEvent = { event, tappedNode ->
                tappedNode?.let { it.scale *= 2.0f }
            }
        ),
        // Frame update callback
        onFrame = { frameTimeNanos ->

        }
    )
}
