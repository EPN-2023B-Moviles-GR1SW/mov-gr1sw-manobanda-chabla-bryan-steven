package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormularioZoologico : AppCompatActivity() {
    val arregloZoologico = BDMemoria.arregloZoologico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_zoologico)

        val crearZoo = findViewById<Button>(R.id.btn_crear_zoo)
        crearZoo.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_zoo_id).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_zoo_nombre).text.toString()
            val ubicacion = findViewById<EditText>(R.id.input_zoo_ubicacion).text.toString()
            val capacidad = findViewById<EditText>(R.id.input_zoo_capacidad).text.toString().toInt()
            val tamanio = findViewById<EditText>(R.id.input_zoo_tamanio).text.toString().toDouble()
            val descripcion = findViewById<EditText>(R.id.input_zoo_descripcion).text.toString()

            arregloZoologico.add(Zoologico(id,nombre,ubicacion, capacidad,tamanio,descripcion))
            finish()
        }
    }
}