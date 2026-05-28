package com.example.ejerciciolab10.ejerciciola109

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

val WarmFondoCanvas = Color(0xFFFAF6F0)
val WarmPuroCard = Color(0xFFFFFFFF)
val WarmPastelBurbuja = Color(0xFFF5EBE0)
val WarmAccent = Color(0xFFBC6C25)
val WarmSubtitulos = Color(0xFF606C38)
val TextoOscuroClaro = Color(0xFF283618)
val WarmRojoBorrar = Color(0xFFF7D6C8)
val TextoRojoBorrar = Color(0xFFAE2012)

@Composable
fun ListaPictogramasScreen(navController: NavHostController, lista: SnapshotStateList<PictogramaModel>) {
    Scaffold(
        containerColor = WarmFondoCanvas,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = WarmAccent,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                onClick = { navController.navigate("picNuevo") }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nueva actividad", modifier = Modifier.size(26.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "U-PAD",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoOscuroClaro,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Sistema de Rutinas",
                        fontSize = 16.sp,
                        color = WarmSubtitulos,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 1.dp, bottom = 12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (lista.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay actividades registradas en el sistema.",
                            color = WarmSubtitulos,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(lista) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmPuroCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.nameLocal,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuroClaro
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Surface(
                                    color = WarmPastelBurbuja,
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Text(
                                        text = item.categoryLocal.ifEmpty { "General" },
                                        fontSize = 11.sp,
                                        color = WarmAccent,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { navController.navigate("picEditar/${item.id}") },
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = WarmPastelBurbuja)
                                ) {
                                    Icon(Icons.Outlined.Edit, contentDescription = "Editar", tint = WarmAccent, modifier = Modifier.size(20.dp))
                                }
                                IconButton(
                                    onClick = { navController.navigate("picEliminar/${item.id}") },
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = WarmRojoBorrar)
                                ) {
                                    Icon(Icons.Outlined.Delete, contentDescription = "Eliminar", tint = TextoRojoBorrar, modifier = Modifier.size(20.dp))
                                }
                            }
                        }

                        if (item.imagenesExtraidas.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                item.imagenesExtraidas.take(3).forEach { url ->
                                    AsyncImage(
                                        model = url,
                                        contentDescription = "Imagen de la actividad",
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(WarmFondoCanvas)
                                            .padding(4.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FormularioPictogramaScreen(navController: NavHostController, lista: SnapshotStateList<PictogramaModel>, pid: Int, servicio: PictogramaApiService) {
    val esNuevo = (pid == 0)
    var nombreInput by remember { mutableStateOf(if (esNuevo) "" else lista.find { it.id == pid }?.nameLocal ?: "") }
    var categoriaInput by remember { mutableStateOf(if (esNuevo) "" else lista.find { it.id == pid }?.categoryLocal ?: "") }
    var cargando by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmFondoCanvas)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(containerColor = WarmPuroCard),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atras", tint = WarmAccent)
            }

            Text(
                text = if (esNuevo) "Nueva Actividad" else "Modificar Tarea",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuroClaro,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = nombreInput,
                onValueChange = { nombreInput = it },
                label = { Text("¿Que vas a hacer?") },
                placeholder = { Text("Ej: Ir al colegio") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarmPuroCard, RoundedCornerShape(10.dp))
            )

            OutlinedTextField(
                value = categoriaInput,
                onValueChange = { categoriaInput = it },
                label = { Text("Categoria / Momento del dia") },
                placeholder = { Text("Ej: Dia, Tarde o Noche") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarmPuroCard, RoundedCornerShape(10.dp))
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WarmAccent, contentColor = Color.White),
            enabled = nombreInput.isNotBlank() && !cargando,
            onClick = {
                cargando = true
                coroutineScope.launch {
                    val urlsEncontradas = mutableListOf<String>()
                    try {
                        val palabras = nombreInput.trim().lowercase().split("\\s+".toRegex())
                            .filter { it.isNotBlank() }

                        for (palabra in palabras.take(3)) {
                            try {
                                val res = servicio.buscarPictograma(palabra.trim())
                                if (!res.isNullOrEmpty()) {
                                    urlsEncontradas.add("https://api.arasaac.org/api/pictograms/${res.first().id}")
                                }
                            } catch (apiError: Exception) {
                                apiError.printStackTrace()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        if (urlsEncontradas.isEmpty()) {
                            urlsEncontradas.add("https://api.arasaac.org/api/pictograms/5432")
                        }

                        if (esNuevo) {
                            val nuevoId = (lista.maxOfOrNull { it.id } ?: 0) + 1
                            lista.add(
                                PictogramaModel(
                                    id = nuevoId,
                                    nameLocal = nombreInput,
                                    categoryLocal = categoriaInput.ifBlank { "Rutina" },
                                    imagenesExtraidas = urlsEncontradas
                                )
                            )
                        } else {
                            val index = lista.indexOfFirst { it.id == pid }
                            if (index != -1) {
                                lista[index] = lista[index].copy(
                                    nameLocal = nombreInput,
                                    categoryLocal = categoriaInput.ifBlank { "Rutina" },
                                    imagenesExtraidas = urlsEncontradas
                                )
                            }
                        }
                        cargando = false
                        navController.popBackStack()
                    }
                }
            }
        ) {
            if (cargando) {
                CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.5.dp)
            } else {
                Text("Guardar Actividad", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = { navController.popBackStack() }
        ) {
            Text("Cancelar", fontSize = 16.sp, color = WarmSubtitulos, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SkinnerEliminarScreen(navController: NavHostController, lista: SnapshotStateList<PictogramaModel>, pid: Int) {
    AlertDialog(
        onDismissRequest = { navController.popBackStack() },
        shape = RoundedCornerShape(16.dp),
        containerColor = WarmPuroCard,
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("¿Quitar de Rutinas?", fontWeight = FontWeight.Bold, color = TextoOscuroClaro, textAlign = TextAlign.Center)
            }
        },
        text = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Esta actividad desaparecerá.", color = WarmSubtitulos, textAlign = TextAlign.Center)
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = TextoRojoBorrar, contentColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    lista.removeAll { it.id == pid }
                    navController.popBackStack()
                }
            ) { Text("Eliminar", fontWeight = FontWeight.SemiBold) }
        },
        dismissButton = {
            TextButton(
                onClick = { navController.popBackStack() }
            ) { Text("Conservar", color = TextoOscuroClaro, fontWeight = FontWeight.Medium) }
        }
    )
}