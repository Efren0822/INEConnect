package com.ineconnect.Componentes

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.navigation.NavController

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class User(
    val username: String = "",
    val password: String = ""
)

fun registerUser(username: String, password: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val db = Firebase.firestore
    val usersRef = db.collection("Usuarios")


    usersRef.whereEqualTo("username", username).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                val user = User(username, password)
                usersRef.add(user)
                    .addOnSuccessListener {
                        onSuccess(username)
                    }
                    .addOnFailureListener { exception ->
                        onError("Error al registrar el usuario: ${exception.message}")
                    }
            } else {

                onError("El usuario ya existe")
            }
        }
        .addOnFailureListener { exception ->
            onError("Error al buscar el usuario: ${exception.message}")
        }
}

@Composable
fun Registro(navController: NavController, onBackClick: () -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            registerUser(username.text, password.text,
                onSuccess = {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                },
                onError = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text("Ir a Login")
        }
    }
}
