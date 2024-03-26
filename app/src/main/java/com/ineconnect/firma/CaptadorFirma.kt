package com.ineconnect.firma

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad
import android.widget.LinearLayout

class MainActivity1 : AppCompatActivity() {

    private lateinit var mSignaturePad: SignaturePad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear un layout linear vertical como contenedor principal
        val layout = LinearLayout(this)
        layout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.orientation = LinearLayout.VERTICAL
        layout.setBackgroundColor(Color.WHITE)

        // Crear SignaturePad
        mSignaturePad = SignaturePad(this, null)
        mSignaturePad.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(mSignaturePad)

        // Crear botón para guardar la firma
        val saveButton = Button(this)
        saveButton.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        saveButton.text = "Guardar Firma"
        saveButton.setOnClickListener { saveSignature() } // Aquí se llama a la función saveSignature()
        layout.addView(saveButton)

        // Establecer el layout como el contenido de la actividad
        setContentView(layout)

        // Configurar escuchador para detectar cuando se completa la firma
        mSignaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                // Evento cuando se empieza a firmar
            }

            override fun onSigned() {
                // Evento cuando la firma ha sido realizada con éxito
            }

            override fun onClear() {
                // Evento cuando se borra la firma
            }
        })
    }

    // Método para guardar la firma
    private fun saveSignature() {
        if (mSignaturePad.isEmpty) {
            // Si la firma está vacía, manejarlo según tus requisitos
            return
        }
        // Obtener la firma como Bitmap
        val signatureBitmap = mSignaturePad.signatureBitmap
        // Aquí puedes guardar el Bitmap como quieras, por ejemplo:
        // Guardarlo en la memoria externa, enviarlo a un servidor, etc.
    }
}
