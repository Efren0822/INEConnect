package com.ineconnect.Componentes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ineconnect.R



fun showAlerta(context: Context, message:String, iconResId:Int){
    val inflater = LayoutInflater.from(context)
    val layout: View = inflater.inflate(R.layout.custom_toast,null)
    val textView=layout.findViewById<TextView>(R.id.toast_text)
    textView.text=message
    val imageView = layout.findViewById<ImageView>(R.id.toast_icon)
    imageView.setImageResource(iconResId)

    with(Toast(context)){
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}