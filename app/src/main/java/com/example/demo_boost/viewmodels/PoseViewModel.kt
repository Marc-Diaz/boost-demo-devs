package com.example.demo_boost.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.poselandmarker.PoseLandmarkerHelper
import com.google.mediapipe.tasks.vision.core.RunningMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PoseViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData para exponer los resultados del pose
    private val _poseResults = MutableLiveData<PoseLandmarkerHelper.ResultBundle?>()
    val poseResults: LiveData<PoseLandmarkerHelper.ResultBundle?> = _poseResults

    // LiveData para notificar errores
    private val _poseErrors = MutableLiveData<String>()
    val poseErrors: LiveData<String> = _poseErrors

    // PoseLandmarkerHelper
    val poseHelper: PoseLandmarkerHelper by lazy {
        PoseLandmarkerHelper(
            context = getApplication<Application>().applicationContext,
            runningMode = RunningMode.LIVE_STREAM,
            currentDelegate = PoseLandmarkerHelper.DELEGATE_CPU,
            poseLandmarkerHelperListener = object : PoseLandmarkerHelper.LandmarkerListener {
                override fun onError(error: String, errorCode: Int) {
                    Log.e("PoseViewModel", "Error: $error (Code: $errorCode)")
                    _poseErrors.postValue(error)
                }

                override fun onResults(resultBundle: PoseLandmarkerHelper.ResultBundle) {
                    _poseResults.postValue(resultBundle)
                }
            }
        )
    }

    // Limpiar recursos cuando se destruye el ViewModel
    override fun onCleared() {
        super.onCleared()
        poseHelper.clearPoseLandmarker()
    }
}
