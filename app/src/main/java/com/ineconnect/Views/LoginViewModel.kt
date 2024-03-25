package com.ineconnect.Views

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {


    fun login(username: String, password: String, onSuccessRedirect: () -> Unit, onError: (String) -> Unit) {
        println("Iniciando sesión con usuario: $username")

        val db = Firebase.firestore
        db.collection("Usuarios")
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                println("Documentos obtenidos: ${documents.size()}")
                if (!documents.isEmpty) {
                    onSuccessRedirect()
                } else {
                    onError("Credenciales incorrectas")
                }
            }
            .addOnFailureListener {
                println("Error al iniciar sesión: ${it.message}")
                onError(it.message ?: "Error desconocido")
            }
    }
    fun navigateToRegistro() {
        // Aquí puedes agregar lógica adicional si es necesario
        println("Navegando al componente de Registro")
    }
}
