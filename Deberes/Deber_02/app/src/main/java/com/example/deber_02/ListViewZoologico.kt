package com.example.deber_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat

class ListViewZoologico : AppCompatActivity() {

    private lateinit var arregloZ : ArrayAdapter<Zoologico>
    private lateinit var zoo: ArrayList<Zoologico>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_zoologico)
        EBaseDatos.BDatos = ESqliteHelper(this)

        zoo = obtenerZooBD()

        val listView = findViewById<ListView>(R.id.lv_item_zoo)
        arregloZ = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            zoo
        )

        listView.adapter = arregloZ
        arregloZ!!.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonAnadirZoo = findViewById<Button>(
            R.id.btn_lv_crear_zoo
        )
        botonAnadirZoo.setOnClickListener {
            irActividad(FormularioZoologico::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        super.onResume()
        val zooActualizado= obtenerZooBD()
        arregloZ.clear()
        arregloZ.addAll(zooActualizado)
        arregloZ.notifyDataSetChanged()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_zoologico, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val zooSeleccionada = zoo[posicionSeleccionada]
        val idSeleccionado = zooSeleccionada.id
        return when (item.itemId){
            R.id.mi_editar_zoo -> {
                val intent = Intent(this, FormularioActualizarZoo::class.java)
                intent.putExtra("zoologico", zooSeleccionada)
                startActivity(intent)
                return true
            }
            R.id.mi_eliminar_zoo -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Zoológico")
                builder.setMessage("¿Estás seguro de que deseas eliminar el zoológico?")
                builder.setPositiveButton("Aceptar") { _, _ ->
                    val dbHelper = ESqliteHelper(this)
                    val conf = dbHelper.eliminarZooFormulario(idSeleccionado)
                    dbHelper.close()
                    zoo.removeAt(posicionSeleccionada)
                    arregloZ.notifyDataSetChanged()
                }
                builder.setNegativeButton(
                    "Cancelar",
                    null
                )
                val  dialogo = builder.create()
                dialogo.show()
                return true
            }
            R.id.mi_mostrar_fauna -> {
                val intent = Intent(this, ListViewFauna::class.java)
                intent.putExtra("zoologico", zooSeleccionada)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase);
        startActivity(intent);
    }

    private fun obtenerZooBD(): ArrayList<Zoologico> {
        //crear instancia a sqlite para acceder a sus datos
        val dbHelper = ESqliteHelper(this)
        val zoo = dbHelper.obtenerTodosZoo()
        dbHelper.close()
        return zoo
    }
}