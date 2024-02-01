package com.example.deber_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import java.text.SimpleDateFormat

class FormularioFauna : AppCompatActivity() {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_fauna)
        EBaseDatos.BDatos = ESqliteHelper(this)
        val zooSeleccionado = intent.getIntExtra("idZoo", -1)

        val crearFauna = findViewById<Button>(R.id.btn_crear_fauna)
        crearFauna.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_fauna_id).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_fauna_nombre).text.toString()
            val fechaNacimientoStr = findViewById<EditText>(R.id.dt_fauna_fecha).text.toString()
            val cantidad = findViewById<EditText>(R.id.input_fauna_cantidad).text.toString().toInt()
            val depredador = findViewById<CheckBox>(R.id.cb_fauna_depredador).isChecked
            val tipo = findViewById<EditText>(R.id.input_fauna_tipo).text.toString()
            val fecha = dateFormat.parse(fechaNacimientoStr)
            EBaseDatos.BDatos!!.crearFauna(
                zooSeleccionado,
                nombre,
                fecha,
                cantidad,
                depredador,
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