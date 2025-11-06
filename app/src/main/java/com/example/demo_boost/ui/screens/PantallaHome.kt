package com.example.demo_boost.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.demo_boost.R

@Composable
fun PantallaHome(navigateTo: () -> Unit){
    val fotos: List<Int> = mutableListOf(
        R.drawable.ic_launcher_background,
    )
    Column(Modifier.fillMaxSize()) {
        Text("INICIO")
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = ""
        )
        Section("Para Ti", fotos = fotos, navigateTo)
    }
}

@Composable
fun Section(nombre: String, fotos: List<Int>, navigateTo: () -> Unit){
    Button(onClick = navigateTo) {
        Text(nombre)
    }
    LazyRow() {
        items(fotos){
            Card() {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "")
            }
        }
    }
}