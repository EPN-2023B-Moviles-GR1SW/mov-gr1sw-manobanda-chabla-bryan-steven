package com.example.examen_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat

class FormularioActualizarFauna : AppCompatActivity() {
    var id: String? = null
    var idZoo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_fauna)

        this.idZoo = intent.getStringExtra("idZoologico")
        this.id = intent.getStringExtra("id")

        val nombre = intent.getStringExtra("nombre")
        val fechaNacimiento = ""+intent.getStringExtra("fechaNacimiento")
        val cantidad = ""+intent.getIntExtra("cantidad", 0)
        val esDepredador = ""+intent.getBooleanExtra("esDepredador",false)
        val tipo = intent.getStringExtra("tipo")

        val tid = findViewById<EditText>(R.id.input_fauna_actualizar_id)
        tid.setText(this.id, TextView.BufferType.EDITABLE)


        val tnombre = findViewById<EditText>(R.id.input_fauna_actualizar_nombre)
        tnombre.setText(nombre, TextView.BufferType.EDITABLE)

        val tfechaNacimiento = findViewById<EditText>(R.id.dt_fauna_actualizar_fecha)
        tfechaNacimiento.setText(fechaNacimiento, TextView.BufferType.EDITABLE)

        val tcantidad = findViewById<EditText>(R.id.input_fauna_actualizar_cantidad)
        tcantidad.setText(cantidad, TextView.BufferType.EDITABLE)

        val tdepredador = findViewById<CheckBox>(R.id.cb_fauna_actualizar_depredador)
        tdepredador.isChecked = esDepredador.toBoolean()

        val ttipo = findViewById<EditText>(R.id.input_fauna_actualizar_tipo)
        ttipo.setText(tipo, TextView.BufferType.EDITABLE)

        tid.isEnabled = false

        val actualizarFauna = findViewById<Button>(R.id.btn_actualizar_fauna)
        actualizarFauna.setOnClickListener {
            devolverRespuesta()
        }
    }

    private fun devolverRespuesta(){

        val intentDevlverParametros = Intent()

        intentDevlverParametros.putExtra("idZooDevuelto", this.idZoo)

        intentDevlverParametros.putExtra("idDevuelto", this.id)

        val nombre = findViewById<EditText>(R.id.input_fauna_actualizar_nombre).text.toString()
        intentDevlverParametros.putExtra("nombreModificado", nombre)

        val fechaNacimiento = findViewById<EditText>(R.id.dt_fauna_actualizar_fecha).text.toString()
        intentDevlverParametros.putExtra("fechaNacimientoModificada",fechaNacimiento)

        val cantidad = findViewById<EditText>(R.id.input_fauna_actualizar_cantidad).text.toString().toInt()
        intentDevlverParametros.putExtra("cantidadModificado", cantidad)

        val esDepredador = findViewById<CheckBox>(R.id.cb_fauna_actualizar_depredador).isChecked
        intentDevlverParametros.putExtra("esDepredadorModificado",esDepredador)

        val tipo = findViewById<EditText>(R.id.input_fauna_actualizar_tipo).text.toString()
        intentDevlverParametros.putExtra("tipoModificado",tipo)

        setResult(
            RESULT_OK,
            intentDevlverParametros
        )

        finish()

    }
}