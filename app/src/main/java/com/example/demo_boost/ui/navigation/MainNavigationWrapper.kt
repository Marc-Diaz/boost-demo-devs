package com.example.demo_boost.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demo_boost.ui.screens.PantallaAR


import com.example.demo_boost.ui.screens.PantallaHome
import com.example.demo_boost.ui.screens.PantallaParaTi
import com.example.demo_boost.ui.screens.PantallaTendencias
import com.example.demo_boost.ui.screens.PantallaPermisos
@Composable
fun NavigationWrapper(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(navController, Destinations.PantallaPermisos) {
        composable<Destinations.PantallaPermisos> {
            PantallaPermisos { navController.navigate(Destinations.PantallaHome) }
        }
        composable<Destinations.PantallaHome> {
            PantallaHome(innerPadding){
                navController.navigate(it)
            }
        }
        composable<Destinations.PantallaParaTi> {
            Log.d("ParaTi", "ParaTi")
            PantallaParaTi(innerPadding)
        }
        composable<Destinations.PantallaTendencias>{
            Log.d("TENDENCIAS", "TENDENCIAS")
            PantallaTendencias(innerPadding)
        }
        composable<Destinations.PantallaCamara>{
            PantallaAR(arSession)
        }
    }
}