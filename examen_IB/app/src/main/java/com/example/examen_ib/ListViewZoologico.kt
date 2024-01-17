package com.example.examen_ib

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
    val arregloZoologico = BDMemoria.arregloZoologico
    var adaptador: ArrayAdapter<Zoologico>? = null
    var idItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_zoologico)

        val listView = findViewById<ListView>(R.id.lv_item_zoo)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloZoologico
        )

        listView.adapter = adaptador
        adaptador!!.notifyDataSetChanged()

        val botonAnadirZoo = findViewById<Button>(
            R.id.btn_lv_crear_zoo
        )
        botonAnadirZoo.setOnClickListener {
            irActividad(FormularioZoologico::class.java)
        }

        val tvInfoZoologico = findViewById<TextView>(R.id.tv_info_zoo)

        listView.setOnItemClickListener { _, _, position, _ ->
            val zoologicoSeleccionado = arregloZoologico[position]
            tvInfoZoologico.text = obtenerInformacionZoologico(zoologicoSeleccionado)
            tvInfoZoologico.visibility = View.VISIBLE
        }

        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista(adaptador!!)
    }

    fun actualizarLista(
        adaptador: ArrayAdapter<Zoologico>?
    ){
        adaptador?.notifyDataSetChanged()
    }

    fun actualizarZoologico(){
        val zooSeleccionado = arregloZoologico[idItemSeleccionado]
        val intent = Intent(this, FormularioActualizarZoo::class.java)
        intent.putExtra("zoologico", zooSeleccionado)
        startActivity(intent)
    }

    fun mostrarFaunaZoo(){
        val zooSeleccionado = arregloZoologico[idItemSeleccionado]
        val intent = Intent(this, ListViewFauna::class.java)
        intent.putExtra("zoologico", zooSeleccionado)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_zoologico, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_zoo -> {
                actualizarZoologico()
                return true
            }
            R.id.mi_eliminar_zoo -> {
                abrirDialogo()
                return true
            }
            R.id.mi_mostrar_fauna -> {
                mostrarFaunaZoo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Zoológico")
        builder.setMessage("¿Estás seguro de que deseas eliminar el zoológico ${arregloZoologico[idItemSeleccionado].nombre}?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            arregloZoologico.removeAt(idItemSeleccionado)
            actualizarLista(adaptador)
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase);
        startActivity(intent);
    }

    private fun obtenerInformacionZoologico(zoologico: Zoologico): Spanned {
        val info = ("<b>Nombre:</b> ${zoologico.nombre}<br/>" +
                "<b>Ubicación:</b> ${zoologico.ubicacion}<br/>" +
                "<b>Descripción:</b> ${zoologico.descripcion}<br/>" +
                "<b>Capacidad:</b> ${zoologico.capacidad}<br/>" +
                "<b>Tamaño:</b> ${zoologico.tamanio}")
        return HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}