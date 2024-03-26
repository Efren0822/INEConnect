package com.ineconnect.Views

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class MenuViewModel : ViewModel() {
    private val db = Firebase.firestore

    fun obtenerDatosUsuario(userId: String, onComplete: (DocumentSnapshot?) -> Unit) {
        db.collection("DatosPersonales")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                onComplete(document)
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }

    suspend fun obtenerDatosDeColeccion(): List<Map<String, String>> {
        val datosList = mutableListOf<Map<String, String>>()

        try {
            val querySnapshot = db.collection("DatosPersonales").get().await()

            for (document in querySnapshot) {
                val datos = mutableMapOf<String, String>()
                datos["nombreCompleto"] = document.getString("nombreCompleto") ?: ""
                datos["domicilio"] = document.getString("domicilio") ?: ""
                datos["clave"] = document.getString("clave") ?: ""
                datos["curp"] = document.getString("curp") ?: ""
                datos["fechaNacimiento"] = document.getString("fechaNacimiento") ?: ""
                datos["sexo"] = document.getString("sexo") ?: ""
                datosList.add(datos)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return datosList
    }
}
