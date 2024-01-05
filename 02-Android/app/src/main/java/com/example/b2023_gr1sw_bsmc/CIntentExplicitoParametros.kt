package com.example.b2023_gr1sw_bsmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        mostrarSnackbar("${nombre} ${apellido} ${edad}")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener { devolverRespuesta() }
    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent ()
        intentDevolverParametros.putExtra("nombreModificado", "Steven")
        intentDevolverParametros.putExtra("edadModificado", 24)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )//se indica resultado OK y opcional se devuelve parametro de intent
        finish()
    }

    fun mostrarSnackbar(texto:String){
        com.google.android.material.snackbar.Snackbar
            .make(
                findViewById(R.id.id_layout_intents),
                texto,
                Snackbar.LENGTH_LONG
            )
            .show()
    }
}