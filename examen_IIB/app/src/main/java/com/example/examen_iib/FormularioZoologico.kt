package com.example.examen_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormularioZoologico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_zoologico)

        val crearZoo = findViewById<Button>(R.id.btn_crear_zoo)
        crearZoo.setOnClickListener {
            crearZoologico()
        }
    }

        private fun crearZoologico(){
            val intentDevolverParametros = Intent()

            val nombre = findViewById<EditText>(R.id.input_zoo_nombre).text.toString()
            intentDevolverParametros.putExtra("nombreModificado", nombre)

            val ubicacion = findViewById<EditText>(R.id.input_zoo_ubicacion).text.toString()
            intentDevolverParametros.putExtra("ubicacionModificado", ubicacion)

            val capacidad = findViewById<EditText>(R.id.input_zoo_capacidad).text.toString().toInt()
            intentDevolverParametros.putExtra("capacidadModificada", capacidad)

            val tamanio = findViewById<EditText>(R.id.input_zoo_tamanio).text.toString().toDouble()
            intentDevolverParametros.putExtra("tamanioModificado", tamanio)

            val descripcion = findViewById<EditText>(R.id.input_zoo_descripcion).text.toString()
            intentDevolverParametros.putExtra("descripcionModificada",  descripcion)

            setResult(
                RESULT_OK,
                intentDevolverParametros
            )
            finish()
        }
    }
