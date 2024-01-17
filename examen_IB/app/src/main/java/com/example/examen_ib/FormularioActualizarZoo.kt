package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormularioActualizarZoo : AppCompatActivity() {
    var arregloZoologico = BDMemoria.arregloZoologico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_zoo)

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

        val idZoo = id.text.toString().toInt()
        val ind = buscarIndice(idZoo)

        id.isEnabled = false

        val actualizarZoo = findViewById<Button>(R.id.btn_actualizar_zoo)
        actualizarZoo.setOnClickListener {
            arregloZoologico[ind].id  = id.text.toString().toInt()
            arregloZoologico[ind].nombre = nombre.text.toString()
            arregloZoologico[ind].ubicacion = ubicacion.text.toString()
            arregloZoologico[ind].capacidad = capacidad.text.toString().toInt()
            arregloZoologico[ind].tamanio = tamanio.text.toString().toDouble()
            arregloZoologico[ind].descripcion = descripcion.text.toString()
            finish()
        }
    }

    fun buscarIndice(idBuscado:Int):Int{
        var idEncontrado: Int = -1

        for (zoo in arregloZoologico) {
            if (zoo.id == idBuscado) {
                idEncontrado = arregloZoologico.indexOf(zoo)
                break
            }
        }
        return idEncontrado
    }
}