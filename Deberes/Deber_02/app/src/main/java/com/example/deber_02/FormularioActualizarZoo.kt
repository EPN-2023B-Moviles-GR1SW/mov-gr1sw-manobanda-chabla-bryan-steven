package com.example.deber_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class FormularioActualizarZoo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_zoo)
        EBaseDatos.BDatos = ESqliteHelper(this)

        val zooSeleccionado = intent.getSerializableExtra("zoologico") as Zoologico

        val id = findViewById<EditText>(R.id.input_zoo_actualizar_id)
        val nombre = findViewById<EditText>(R.id.input_zoo_actualizar_nombre)
        val ubicacion = findViewById<EditText>(R.id.input_zoo_actualizar_ubicacion)
        val capacidad = findViewById<EditText>(R.id.input_zoo_actualizar_capacidad)
        val tamanio = findViewById<EditText>(R.id.input_zoo_actualizar_tamanio)
        val descripcion = findViewById<EditText>(R.id.input_zoo_actualizar_descripcion)

        id.setText(zooSeleccionado.id.toString())
        nombre.setText(zooSeleccionado.nombre.toString())
        ubicacion.setText(zooSeleccionado.ubicacion.toString())
        capacidad.setText(zooSeleccionado.capacidad.toString())
        tamanio.setText(zooSeleccionado.tamanio.toString())
        descripcion.setText(zooSeleccionado.descripcion.toString())

        id.isEnabled = false

        val actualizarZoo = findViewById<Button>(R.id.btn_actualizar_zoo)
        actualizarZoo.setOnClickListener {
            val id  = id.text.toString().toInt()
            val nombre = nombre.text.toString()
            val ubicacion = ubicacion.text.toString()
            val capacidad = capacidad.text.toString().toInt()
            val tamanio = tamanio.text.toString().toDouble()
            val descripcion = descripcion.text.toString()
            finish()
            EBaseDatos.BDatos!!.actualizarZooFormulario(nombre,ubicacion, capacidad,tamanio,descripcion, id)
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