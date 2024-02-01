package com.example.deber_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import java.text.SimpleDateFormat

class FormularioActualizarFauna : AppCompatActivity() {

    val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actualizar_fauna)
        EBaseDatos.BDatos = ESqliteHelper(this)
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

        id.isEnabled = false

        val actualizarFauna = findViewById<Button>(R.id.btn_actualizar_fauna)
        actualizarFauna.setOnClickListener {
            val fecha = dateFormat.parse(fechaNacimientoStr.text.toString())

            val idFauna = id.text.toString().toInt()
            val nombre = nombre.text.toString()
            val fechaNacimiento = fecha
            val cantidad = cantidad.text.toString().toInt()
            val esDepredador = depredador.isChecked
            val tipo = tipo.text.toString()
            finish()
            EBaseDatos.BDatos!!.actualizarFaunaFormulario(
                idFauna,
                nombre,
                fecha,
                cantidad,
                esDepredador,
                tipo
            )
            actualizarFauna()
        }
    }
    private fun actualizarFauna(){
        val listViewFauna = findViewById<ListView>(R.id.lv_item_fauna)

        if (listViewFauna != null ) {
            val adaptador = listViewFauna.adapter as ArrayAdapter<Fauna>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }

        } else {
            finish()
        }
    }
}