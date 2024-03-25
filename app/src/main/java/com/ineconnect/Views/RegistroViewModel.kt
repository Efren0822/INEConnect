package com.ineconnect.Views

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroViewModel : ViewModel() {
    private val db = Firebase.firestore

    fun registro(username: String, password: String, onSuccessRedirect: () -> Unit, onError: (String) -> Unit) {
        println("Registrando nuevo usuario: $username")

        val db = Firebase.firestore
        val user = hashMapOf(
            "username" to username,
            "password" to password
        )

        db.collection("Usuarios")
            .add(user)
            .addOnSuccessListener {
                println("Usuario registrado con éxito")
                onSuccessRedirect()
            }
            .addOnFailureListener {
                println("Error al registrar usuario: ${it.message}")
                onError(it.message ?: "Error desconocido")
            }
    }

}
