package com.example.b2023_gr1sw_bsmc

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {

    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var posicionitemSeleccionado = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //obtener el id del ArrayList Seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionitemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar -> {
                mostrarSnackbar("${posicionitemSeleccionado}")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar("${posicionitemSeleccionado}")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_view),
                texto,
                Snackbar.LENGTH_LONG
            )
            .show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view
        )
        botonAnadirListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }

        registerForContextMenu(listView)
    }

    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(
                1,
                "Bryan",
                "descripcion"
            )
        )
        adaptador.notifyDataSetChanged()
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                mostrarSnackbar("Eliminar Aceptado")
            }
        )
        builder.setNegativeButton(
            "cancelar",
            null
        )

        val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
        val seleccionPrevia = booleanArrayOf(
            true, //lunes seleccionado
        false, //martes no seleccionado
        false, // miercoles no seleccionado
        )
        builder.setMultiChoiceItems(
            opciones, seleccionPrevia,
            {dialog, which, isChecked ->
                mostrarSnackbar("Dio clic en el item ${which}")
            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }
}