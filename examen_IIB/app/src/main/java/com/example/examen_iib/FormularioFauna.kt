package com.example.examen_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.text.SimpleDateFormat

class FormularioFauna : AppCompatActivity() {

    var idZoo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_fauna)

        this.idZoo = intent.getStringExtra("id")!!

        val crearFauna = findViewById<Button>(R.id.btn_crear_fauna)
        crearFauna.setOnClickListener {
            crearFauna()
        }
    }

    private  fun crearFauna(){

        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("idZooDevuelto", this.idZoo)

        var nombre = findViewById<EditText>(R.id.input_fauna_nombre).text.toString()
        intentDevolverParametros.putExtra("nombreModificado", nombre)

        var fecha = findViewById<EditText>(R.id.dt_fauna_fecha).text.toString()
        intentDevolverParametros.putExtra("fechaNacimientoModificada", fecha)

        var cantidad = findViewById<EditText>(R.id.input_fauna_cantidad).text.toString().toInt()
        intentDevolverParametros.putExtra("cantidadModificado",cantidad)

        var depredador = findViewById<EditText>(R.id.cb_fauna_depredador).text.toString().toBoolean()
        intentDevolverParametros.putExtra("esDepredadorModificado",depredador)

        var tipo = findViewById<EditText>(R.id.input_fauna_tipo).text.toString()
        intentDevolverParametros.putExtra("tipoModificado",tipo)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()

    }

}