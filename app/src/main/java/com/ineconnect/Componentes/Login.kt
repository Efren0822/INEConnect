package com.ineconnect.Componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Login(onLoginClick: (String, String) -> Unit, onRegisterClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {

            validateUser(username, password, onSuccess = {

                onLoginClick(username, password)
            }, onError = { error ->

                println(error)
            })
        }) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRegisterClick() }) {
            Text("Registro de Usuario")
        }
    }
}

fun validateUser(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val db = Firebase.firestore
    val usersRef = db.collection("Usuarios")

    usersRef.whereEqualTo("username", username).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                onError("Usuario no encontrado")
            } else {
                val userDoc = documents.documents.first()
                val actualPassword = userDoc.getString("password")
                if (actualPassword == password) {
                    onSuccess()
                } else {
                    onError("Contraseña incorrecta")
                }
            }
        }
        .addOnFailureListener { exception ->
            onError("Error al iniciar sesión: ${exception.message}")
        }
}
