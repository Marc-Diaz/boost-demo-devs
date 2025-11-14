import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.demo_boost.viewmodels.PoseViewModel
import com.example.demo_boost.utils.landmarkToWorldPosition
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import com.google.ar.sceneform.collision.Sphere
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import io.github.sceneview.ar.ARScene

import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.node.Node

import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

@Composable
fun PantallaAR(poseViewModel: PoseViewModel) {
    val context = LocalContext.current

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val cameraNode = rememberARCameraNode(engine)
    val view = rememberView(engine)
    val collisionSystem = rememberCollisionSystem(view)

    var planeRenderer by remember { mutableStateOf(true) }
    var trackingFailureReason by remember { mutableStateOf<TrackingFailureReason?>(null) }
    var frame by remember { mutableStateOf<Frame?>(null) }
    var poseLandmarks by remember { mutableStateOf<PoseLandmarkerResult?>(null) }

    // Lista de AnchorNodes de landmarks
    val landmarkAnchors = remember { mutableStateListOf<AnchorNode>() }

    // Observamos resultados de MediaPipe
    LaunchedEffect(poseViewModel) {
        poseViewModel.poseResults.observeForever { result ->
            poseLandmarks = result?.results?.getOrNull(0)
        }
    }

    ARScene(
        modifier = Modifier.fillMaxSize(),
        childNodes = landmarkAnchors, // agregamos los anchors a la escena
        engine = engine,
        view = view,
        modelLoader = modelLoader,
        collisionSystem = collisionSystem,
        cameraNode = cameraNode,
        planeRenderer = planeRenderer,
        sessionConfiguration = { session, config ->
            config.depthMode =
                if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC))
                    Config.DepthMode.AUTOMATIC else Config.DepthMode.DISABLED
            config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },
        onTrackingFailureChanged = { trackingFailureReason = it },
        onSessionUpdated = { session, updatedFrame ->
            frame = updatedFrame
            poseLandmarks?.landmarks()?.forEachIndexed { index, landmarkList ->
                // Para cada landmark, calculamos su posición en el mundo
                for (normalizedLandmark in landmarkList) {
                    val worldPosition = landmarkToWorldPosition(normalizedLandmark, updatedFrame)

                    // Revisamos si ya existe un AnchorNode para este landmark
                    val anchorNode = landmarkAnchors.getOrNull(index)
                    if (anchorNode == null) {
                        // Creamos un anchor en esa posición
                        val anchor = session.createAnchor(
                            com.google.ar.core.Pose(
                                floatArrayOf(worldPosition.x, worldPosition.y, worldPosition.z),
                                floatArrayOf(0f, 0f, 0f, 1f) // rotación identidad
                            )
                        )
                        anchor?.let {
                            val newAnchorNode = AnchorNode(it)
                            newAnchorNode.worldPosition = worldPosition

                            // Creamos un nodo esférico para representar el landmark
                            val sphereNode = Node().apply {
                                renderable = Sphere(0.02f)
                                material.color = android.graphics.Color.YELLOW
                            }
                            newAnchorNode.addChild(sphereNode)

                            landmarkAnchors.add(newAnchorNode)
                        }
                    } else {
                        // Actualizamos la posición del AnchorNode existente
                        anchorNode.worldPosition = worldPosition
                    }
                }
            }
        },
        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = { motionEvent, node ->
                if (node == null) {
                    frame?.hitTest(motionEvent.x, motionEvent.y)
                        ?.firstOrNull { it.isValid(depthPoint = false, point = false) }
                        ?.createAnchorOrNull()
                        ?.let { anchor ->
                            val anchorNode = AnchorNode(anchor)
                            landmarkAnchors.add(anchorNode)
                        }
                }
            }
        )
    )
}
