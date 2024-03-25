package com.ineconnect.Views

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatosPersonalesViewModel : ViewModel() {
    private val db = Firebase.firestore

    fun guardarDatos(
        userId: String,
        nombreCompleto: String,
        domicilio: String,
        clave: String,
        curp: String,
        fechaNacimiento: String,
        sexo: String,
        onComplete: (Boolean) -> Unit
    ) {
        val datos = hashMapOf(
            "nombreCompleto" to nombreCompleto,
            "domicilio" to domicilio,
            "clave" to clave,
            "curp" to curp,
            "fechaNacimiento" to fechaNacimiento,
            "sexo" to sexo,
            "usuario" to db.collection("Usuarios").document(userId)
        )

        db.collection("DatosPersonales")
            .add(datos)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}
