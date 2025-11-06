package com.example.demo_boost.ui.navigation
import kotlinx.serialization.Serializable


sealed class Destinations {
    @Serializable
    object PantallaHome: Destinations()

    @Serializable
    object PantallaParaTi: Destinations()

}
