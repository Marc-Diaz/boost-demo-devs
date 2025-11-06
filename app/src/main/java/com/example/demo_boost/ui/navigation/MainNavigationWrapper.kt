package com.example.demo_boost.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo_boost.ui.navigation.Destinations
import com.example.demo_boost.ui.screens.PantallaHome
import com.example.demo_boost.ui.screens.PantallaParaTi

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Destinations.PantallaHome) {
        composable<Destinations.PantallaHome> {
            PantallaHome(){
                navController.navigate(Destinations.PantallaParaTi)
            }
        }
        composable<Destinations.PantallaParaTi> {
            PantallaParaTi()
        }
    }
}