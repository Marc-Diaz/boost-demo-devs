package com.example.demo_boost.viewmodels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.demo_boost.utils.PermissionStatus
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import kotlin.collections.set


class ARViewModel(): ViewModel() {
    var arSession: Session? = null
        private set

    // Inicializa la sesión AR
    fun initializeSession(context: Context): Boolean {
        return try {
            arSession = Session(context)
            true
        } catch (e: UnavailableArcoreNotInstalledException) {
            false
        } catch (e: UnavailableApkTooOldException) {
            false
        } catch (e: UnavailableSdkTooOldException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    fun resumeSession() {
        arSession?.resume()
    }

    fun pauseSession() {
        arSession?.pause()
    }

    override fun onCleared() {
        super.onCleared()
        arSession?.close()
    }
}
