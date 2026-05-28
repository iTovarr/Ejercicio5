package com.example.ejerciciolab10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ejerciciolab10.ejerciciola109.MainPictogramaApp
import com.example.ejerciciolab10.ui.theme.Ejerciciolab10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejerciciolab10Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Envolvemos tu app con un Box usando el innerPadding para respetar los márgenes de la pantalla
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainPictogramaApp()
                    }
                }
            }
        }
    }
}