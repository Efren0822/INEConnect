package com.ineconnect.Componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun DatosPersonales(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var nombreCompleto by remember { mutableStateOf(TextFieldValue()) }
    var fechaNacimiento by remember { mutableStateOf(TextFieldValue()) }
    var sexo by remember { mutableStateOf(TextFieldValue()) }
    var nacionalidad by remember { mutableStateOf(TextFieldValue()) }
    var lugarNacimiento by remember { mutableStateOf(TextFieldValue()) }
    var domicilio by remember { mutableStateOf(TextFieldValue()) }
    var claveElectoral by remember { mutableStateOf(TextFieldValue()) }
    var curp by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = nombreCompleto,
            onValueChange = { nombreCompleto = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { fechaNacimiento = it },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = sexo,
            onValueChange = { sexo = it },
            label = { Text("Sexo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nacionalidad,
            onValueChange = { nacionalidad = it },
            label = { Text("Nacionalidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = lugarNacimiento,
            onValueChange = { lugarNacimiento = it },
            label = { Text("Lugar de Nacimiento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = domicilio,
            onValueChange = { domicilio = it },
            label = { Text("Domicilio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = claveElectoral,
            onValueChange = { claveElectoral = it },
            label = { Text("Clave Electoral") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = curp,
            onValueChange = { claveElectoral = it },
            label = { Text("Clave Electoral") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Aquí deberías agregar la lógica para guardar los datos en Firestore
            guardarRegistroEnFirestore(
                nombreCompleto.text,
                fechaNacimiento.text,
                sexo.text,
                nacionalidad.text,
                lugarNacimiento.text,
                domicilio.text,
                claveElectoral.text,
                curp.text
            )
            onRegisterSuccess()
        }) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onBackClick() }) {
            Text("Regresar")
        }
    }
}

fun guardarRegistroEnFirestore(
    nombreCompleto: String,
    fechaNacimiento: String,
    sexo: String,
    nacionalidad: String,
    lugarNacimiento: String,
    domicilio: String,
    claveElectoral: String,
    curp : String
) {
    val db = Firebase.firestore
    val registrosRef = db.collection("DatosPersonales")

    val registro = hashMapOf(
        "nombreCompleto" to nombreCompleto,
        "fechaNacimiento" to fechaNacimiento,
        "sexo" to sexo,
        "nacionalidad" to nacionalidad,
        "lugarNacimiento" to lugarNacimiento,
        "domicilio" to domicilio,
        "claveElectoral" to claveElectoral,
        "curp" to curp
    )

    registrosRef.add(registro)
        .addOnSuccessListener { documentReference ->
            println("Registro exitoso con ID: ${documentReference.id}")
            // Aquí puedes mostrar una alerta de registro exitoso si lo deseas
        }
        .addOnFailureListener { e ->
            println("Error al registrar: $e")
            // Aquí puedes mostrar una alerta de error si lo deseas
        }
}