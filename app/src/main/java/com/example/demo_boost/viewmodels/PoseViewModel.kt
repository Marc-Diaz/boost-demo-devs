package com.example.demo_boost.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.poselandmarker.PoseLandmarkerHelper
import com.google.mediapipe.tasks.vision.core.RunningMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PoseViewModel(application: Application) : ViewModel() {

    // Estado que va a contener los resultados del pose
    private var _poseResults = MutableLiveData<PoseLandmarkerHelper.ResultBundle?>(null)
        private set
    val poseResults = _poseResults

    var poseHelper: PoseLandmarkerHelper? = null
        private set

    init {
        viewModelScope.launch(Dispatchers.Main){
            poseHelper = PoseLandmarkerHelper(
                context = application.applicationContext,
                runningMode = RunningMode.LIVE_STREAM,
                currentDelegate = PoseLandmarkerHelper.Companion.DELEGATE_CPU,
                poseLandmarkerHelperListener = object : PoseLandmarkerHelper.LandmarkerListener {
                    override fun onError(error: String, errorCode: Int) {
                        Log.e("PoseViewModel", "Error: $error")
                    }

                    override fun onResults(resultBundle: PoseLandmarkerHelper.ResultBundle) {
                        // Aquí es donde accedes a los resultados
                        _poseResults.value = resultBundle
                    }
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        poseHelper?.clearPoseLandmarker()
    }
}