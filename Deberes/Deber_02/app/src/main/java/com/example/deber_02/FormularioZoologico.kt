package com.example.deber_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class FormularioZoologico : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_zoologico)
        EBaseDatos.BDatos = ESqliteHelper(this)
        val crearZoo = findViewById<Button>(R.id.btn_crear_zoo)
        crearZoo.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_zoo_id).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_zoo_nombre).text.toString()
            val ubicacion = findViewById<EditText>(R.id.input_zoo_ubicacion).text.toString()
            val capacidad = findViewById<EditText>(R.id.input_zoo_capacidad).text.toString().toInt()
            val tamanio = findViewById<EditText>(R.id.input_zoo_tamanio).text.toString().toDouble()
            val descripcion = findViewById<EditText>(R.id.input_zoo_descripcion).text.toString()

            EBaseDatos.BDatos!!.crearZoologico(nombre,ubicacion, capacidad,tamanio,descripcion)
            actualizarListaZoo()
        }
    }

    private fun actualizarListaZoo() {
        val listViewZoo = findViewById<ListView>(R.id.lv_item_zoo)

        if (listViewZoo!= null) {
            val adaptador = listViewZoo.adapter as ArrayAdapter<Zoologico>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }
        } else{
            finish()
        }
    }
}