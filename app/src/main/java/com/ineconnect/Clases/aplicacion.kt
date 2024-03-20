package com.ineconnect.Clases

import android.app.Application
import com.google.firebase.FirebaseApp
class aplicacion : Application( ) {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}