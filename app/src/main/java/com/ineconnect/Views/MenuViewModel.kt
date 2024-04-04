package com.ineconnect.Views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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

    suspend fun obtenerDatosDeColeccion(): List<Map<String, Any>> {
        val datosList = mutableListOf<Map<String, Any>>()

        try {
            val querySnapshot = db.collection("DatosPersonales").get().await()

            for (document in querySnapshot) {
                val datos = mutableMapOf<String, Any>()
                datos["nombreCompleto"] = document.getString("nombreCompleto") ?: ""
                datos["domicilio"] = document.getString("domicilio") ?: ""
                datos["clave"] = document.getString("clave") ?: ""
                datos["curp"] = document.getString("curp") ?: ""
                datos["fechaNacimiento"] = document.getString("fechaNacimiento") ?: ""
                datos["sexo"] = document.getString("sexo") ?: ""
                datos["perfilUrl"] = document.getString("perfilUrl")?:""
                datos["lugarNacimiento"] = document.getString("lugarNacimiento")?:""

                datosList.add(datos)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return datosList
    }
}

fun base64ToBitmap(base64String: String): Bitmap {
    val decodeBytes = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
}