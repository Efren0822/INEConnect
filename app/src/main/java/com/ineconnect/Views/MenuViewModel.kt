package com.ineconnect.Views

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.DocumentSnapshot

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
}
