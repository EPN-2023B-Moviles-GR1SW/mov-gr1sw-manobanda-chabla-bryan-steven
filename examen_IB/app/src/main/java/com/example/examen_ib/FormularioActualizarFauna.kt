package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Date

class FormularioActualizarFauna : AppCompatActivity() {

    var arreglo_fauna = BDMemoria.arregloFauna
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_fauna)

        val faunaSeleccionada = intent.getSerializableExtra("fauna") as Fauna

        val id = findViewById<EditText>(R.id.input_fauna_actualizar_id)
        val nombre = findViewById<EditText>(R.id.input_fauna_actualizar_nombre)
        val fechaNacimientoStr = findViewById<EditText>(R.id.dt_fauna_actualizar_fecha)
        val cantidad = findViewById<EditText>(R.id.input_fauna_actualizar_cantidad)
        val depredador = findViewById<CheckBox>(R.id.cb_fauna_actualizar_depredador)
        val tipo = findViewById<EditText>(R.id.input_fauna_actualizar_tipo)

        id.setText(faunaSeleccionada.id.toString())
        nombre.setText(faunaSeleccionada.nombre.toString())
        fechaNacimientoStr.setText(faunaSeleccionada.fechaNacimiento.toString())
        cantidad.setText(faunaSeleccionada.cantidad.toString())
        depredador.isChecked = faunaSeleccionada.esDepredador
        tipo.setText(faunaSeleccionada.tipo.toString())

        val idFauna = id.text.toString().toInt()
        val ind = buscarIndice(idFauna)

        id.isEnabled = false

        val actualizarFauna = findViewById<Button>(R.id.btn_actualizar_fauna)
        actualizarFauna.setOnClickListener {
            val fecha = dateFormat.parse(fechaNacimientoStr.text.toString())
            arreglo_fauna[ind].id = id.text.toString().toInt()
            arreglo_fauna[ind].nombre = nombre.text.toString()
            arreglo_fauna[ind].fechaNacimiento = fecha
            arreglo_fauna[ind].cantidad = cantidad.text.toString().toInt()
            arreglo_fauna[ind].esDepredador = depredador.isChecked
            arreglo_fauna[ind].tipo = tipo.text.toString()
            finish()
        }
    }

    fun buscarIndice(idBuscado:Int):Int{
        var idEncontrado: Int = -1

        for (fauna in arreglo_fauna) {
            if (fauna.id == idBuscado) {
                idEncontrado = arreglo_fauna.indexOf(fauna)
                break
            }
        }
        return idEncontrado
    }
}