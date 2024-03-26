package com.ineconnect.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ineconnect.Views.MenuViewModel


@Composable
fun Navegacion(viewModel: MenuViewModel, userId: String, navigateToDatosPersonales: () -> Unit) {
    val datosList = remember { mutableStateListOf<Map<String, String>>() }

    LaunchedEffect(Unit) {
        val datos = viewModel.obtenerDatosDeColeccion()
        datosList.addAll(datos)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (datos in datosList) {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Nombre: ${datos["nombreCompleto"] ?: "No hay ningún dato disponible"}")
                    Text("Domicilio: ${datos["domicilio"] ?: ""}")
                    Text("Clave: ${datos["clave"] ?: ""}")
                    Text("CURP: ${datos["curp"] ?: ""}")
                    Text("Fecha de Nacimiento: ${datos["fechaNacimiento"] ?: ""}")
                    Text("Sexo: ${datos["sexo"] ?: ""}")
                    Icon(
                        if (datos["sexo"] == "M") Icons.Default.Person else Icons.Default.Person,
                        contentDescription = "Sexo"
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navigateToDatosPersonales() }) {
                Text("Agregar datos")
            }
        }

    }
}
