package com.ineconnect.Clases
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class FireStore {

    fun getFirestoreCollection() = Firebase.firestore.collection("Usuarios")


    fun readDataFromFirestore() {
        val collectionRef = getFirestoreCollection()

        collectionRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val data = document.data
                    println("Documento: $data")
                }
            }
            .addOnFailureListener { exception ->

                println("Error al leer documentos: $exception")
            }
    }

// Ejemplo de uso de la función para leer datos de Firestore
// Llamar a esta función cuando necesites leer datos de tu colección
// readDataFromFirestore()

}