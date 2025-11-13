package com.example.demo_boost.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo_boost.viewmodels.PoseViewModel

class PoseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PoseViewModel::class.java)) {
            return PoseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}