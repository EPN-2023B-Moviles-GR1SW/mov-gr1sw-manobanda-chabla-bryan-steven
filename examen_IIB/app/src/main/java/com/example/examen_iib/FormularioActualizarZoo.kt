package com.example.examen_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FormularioActualizarZoo : AppCompatActivity() {
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_zoo)

        this.id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val ubicacion = intent.getStringExtra("ubicacion")
        val capacidad = ""+intent.getIntExtra("capacidad", 0)
        val tamanio = ""+intent.getDoubleExtra("tamanio", 0.0)
        val descripcion = intent.getStringExtra("descripcion")

        val tid = findViewById<EditText>(R.id.input_zoo_actualizar_id)
        tid.setText(this.id, TextView.BufferType.EDITABLE)

        val tnombre = findViewById<EditText>(R.id.input_zoo_actualizar_nombre)
        tnombre.setText(nombre, TextView.BufferType.EDITABLE)

        val tubicacion = findViewById<EditText>(R.id.input_zoo_actualizar_ubicacion)
        tubicacion.setText(ubicacion, TextView.BufferType.EDITABLE)

        val tcapacidad = findViewById<EditText>(R.id.input_zoo_actualizar_capacidad)
        tcapacidad.setText(capacidad, TextView.BufferType.EDITABLE)

        val ttamanio = findViewById<EditText>(R.id.input_zoo_actualizar_tamanio)
        ttamanio.setText(tamanio, TextView.BufferType.EDITABLE)

        val tdescripcion = findViewById<EditText>(R.id.input_zoo_actualizar_descripcion)
        tdescripcion.setText(descripcion, TextView.BufferType.EDITABLE)
        tid.isEnabled = false

        val actualizarZoo = findViewById<Button>(R.id.btn_actualizar_zoo)
        actualizarZoo.setOnClickListener {
            try {
                devolverRespuesta()
            } catch (e: Exception) {
                val errorMessage = "Ocurrió un error: ${e.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun devolverRespuesta() {
        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("idDevuelto", this.id)

        val textNombre = findViewById<EditText>(R.id.input_zoo_actualizar_nombre)
        intentDevolverParametros.putExtra("nombreModificado", textNombre.text.toString())

        val textUbicacion = findViewById<EditText>(R.id.input_zoo_actualizar_ubicacion)
        intentDevolverParametros.putExtra("ubicacionModificado", textUbicacion.text.toString())

        val textCapacidad = findViewById<EditText>(R.id.input_zoo_actualizar_capacidad)
        intentDevolverParametros.putExtra("capacidadModificada", textCapacidad.text.toString().toInt())

        val textTamanio = findViewById<EditText>(R.id.input_zoo_actualizar_tamanio)
        intentDevolverParametros.putExtra("tamanioModificado", textTamanio.text.toString().toDouble())

        val textDescripcion = findViewById<EditText>(R.id.input_zoo_actualizar_descripcion)
        intentDevolverParametros.putExtra("descripcionModificada", textDescripcion.text.toString())
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

}