package com.example.demo_boost.utils

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
}